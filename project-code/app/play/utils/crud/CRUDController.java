package play.utils.crud;

import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;
import play.utils.dao.EntityNotFoundException;

import com.avaje.ebean.Page;

public abstract class CRUDController<K, M extends BasicModel<K>> extends
		DynamicTemplateController {

	private final DAO<K, M> dao;

	private final Form<M> form;

	private final Class<K> keyClass;

	private final Class<M> modelClass;

	private String orderBy;

	private int pageSize;

	public CRUDController(DAO<K, M> dao, Form<M> form, Class<K> keyClass,
			Class<M> modelClass, int pagesize, String orderBy) {
		this.dao = dao;
		this.form = form;
		this.keyClass = keyClass;
		this.modelClass = modelClass;
		this.pageSize = pagesize;
		this.orderBy = orderBy;
	}

	public DAO<K, M> getDao() {
		return dao;
	}

	public Form<M> getForm() {
		return form;
	}

	public Result list(int page) {
		Page<M> p = dao.page(page, pageSize(), orderBy());
		return ok(templateForList(), with(Page.class, p));
	}

	protected String orderBy() {
		return orderBy;
	}

	protected int pageSize(){
		return pageSize;
	}

	protected abstract String templateForList();

	protected abstract String templateForForm();

	protected abstract String templateForShow();
	
	protected abstract Call toIndex();

	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");

		return ok(templateForForm(),
				with(keyClass, null).and(Form.class, form));
	}

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");

		Form<M> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForForm(),
					with(keyClass, null).and(Form.class, filledForm));
		} else {
			M model = filledForm.get();
			dao.create(model);
			if (log.isDebugEnabled())
				log.debug("entity created");

			return redirect(toIndex());
		}
	}

	public Result editForm(K key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);

		M model = dao.get(key);
		if (log.isDebugEnabled())
			log.debug("model : " + model);

		Form<M> frm = form.fill(model);
		return ok(templateForForm(), with(keyClass, key).and(Form.class, frm));
	}

	public Result update(K key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);

		M original = dao.get(key);
		Form<M> filledForm = form.fill(original).bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForForm(),
					with(keyClass, key).and(Form.class, filledForm));
		} else {
			M model = filledForm.get();
			model.setKey(key);
			if (log.isDebugEnabled())
				log.debug("model : " + model);
			dao.update(model);
			if (log.isDebugEnabled())
				log.debug("entity updated");

			return redirect(toIndex());
		}
	}


	public Result show(K key) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + key);

		M model = dao.get(key);
		if (log.isDebugEnabled())
			log.debug("model : " + model);

		return ok(templateForShow(), with(modelClass, model));
	}

	public Result delete(K key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);

		try {
			dao.remove(key);
			if (log.isDebugEnabled())
				log.debug("entity deleted");
		} catch (EntityNotFoundException e) {
			if (log.isDebugEnabled())
				log.debug("entity not found for key:" + key);
			flash("error", "entity not found for key:" + key);
		}

		return redirect(toIndex());
	}

}
