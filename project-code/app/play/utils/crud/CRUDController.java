package play.utils.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.Serializable;

public abstract class CRUDController<M, K extends Serializable> extends TemplateController implements
		CRUD<M, K> {

	private static ALogger log = Logger.of(CRUDController.class);

	private final JpaRepository<M, K> repo;

	private final Form<M> form;

	private final Class<K> keyClass;

	private final Class<M> modelClass;

	private Sort sort;

	private int pageSize;

	@Inject
	public CRUDController(ClassLoader classLoader, JpaRepository<M, K> repo, Form<M> form, Class<K> keyClass, Class<M> modelClass, int pageSize,
			Sort sort) {
		super(classLoader);
		this.repo = repo;
		this.form = form;
		this.keyClass = keyClass;
		this.modelClass = modelClass;
		this.pageSize = pageSize;
		this.sort = sort;
	}

	@Inject
	public CRUDController(JpaRepository<M, K> repo, Form<M> form, Class<K> keyClass, Class<M> modelClass, int pageSize,
			Sort sort) {
		super();
		this.repo = repo;
		this.form = form;
		this.keyClass = keyClass;
		this.modelClass = modelClass;
		this.pageSize = pageSize;
		this.sort = sort;
	}

	public JpaRepository<M, K> getRepo() {
		return repo;
	}

	public Class<K> getKeyClass() {
		return keyClass;
	}

	public Class<M> getModelClass() {
		return modelClass;
	}

	public Form<M> getForm() {
		return form;
	}

	public Result list(int page) {
		Page p = repo.findAll(new PageRequest(page, pageSize(), sort()));
		return ok(renderList(p));
	}

	protected Sort sort() {
		return sort;
	}

	protected int pageSize() {
		return pageSize;
	}

	protected abstract String templateForList();

	protected abstract String templateForForm();

	protected abstract String templateForShow();

	protected abstract Call toIndex();
	
	protected Content renderList(Page p) {
		return render(templateForList(), with(Page.class, p));
	}

	protected Content renderForm(Object key, Form<M> form) {
		return render(templateForForm(), with(keyClass, key).and(Form.class, form));
	}

	protected Content renderShow(M model) {
		return render(templateForShow(), with(modelClass, model));
	}

	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");

		return ok(renderForm(null, form));
	}

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");

		Form<M> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured: " + filledForm.errors());

			return badRequest(renderForm(null, filledForm));
		} else {
			M model = filledForm.get();
			repo.save(model);
			if (log.isDebugEnabled())
				log.debug("entity created");

			Call index = toIndex();
			if (log.isDebugEnabled())
				log.debug("index : " + index);
			return redirect(index);
		}
	}

	public Result editForm(K key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);

		M model = repo.findOne(key);
		if (log.isDebugEnabled())
			log.debug("model : " + model);

		Form<M> frm = form.fill(model);
		return ok(renderForm(key, frm));
	}

	public Result update(K key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);

		M original = repo.findOne(key);
		Form<M> filledForm = form.fill(original).bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured: " + filledForm.errors());

			return badRequest(renderForm(key, filledForm));
		} else {
			M model = filledForm.get();
			if (log.isDebugEnabled())
				log.debug("model : " + model);
			repo.save(model);
			if (log.isDebugEnabled())
				log.debug("entity updated");

			Call index = toIndex();
			if (log.isDebugEnabled())
				log.debug("index : " + index);
			return redirect(index);
		}
	}

	public Result show(K key) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + key);

		M model = repo.findOne(key);
		if (log.isDebugEnabled())
			log.debug("model : " + model);

		return ok(renderShow(model));
	}

	public Result delete(K key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);

		try {
			repo.delete(key);
			if (log.isDebugEnabled())
				log.debug("entity deleted");
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("entity not found for key:" + key);
			flash("error", "entity not found for key:" + key);
		}

		Call index = toIndex();
		if (log.isDebugEnabled())
			log.debug("index : " + index);
		return redirect(index);
	}

	public Result read(K key) {
		return show(key);
	}

	public Result list() {
		return list(0);
	}

}
