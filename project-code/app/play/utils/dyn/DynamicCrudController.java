package play.utils.dyn;

import static play.data.Form.form;

import com.avaje.ebean.Page;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.crud.Parameters;
import play.utils.dao.BasicModel;
import play.utils.meta.ModelMetadata;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Dynamic
public class DynamicCrudController extends CRUDController {
	
	private static ALogger log = Logger.of(DynamicCrudController.class);
	
	private ModelMetadata model;
	private String templateList;
	private String templateForm;
	private String templateShow;
	private Call indexCall;

	public DynamicCrudController(ClassLoader classLoader, ModelMetadata model, Call indexCall) {
		super(classLoader, new DynamicDAO(model), form(model.getType()), model.getKeyField().getType(), model.getType(), 10, model.getKeyField().getField().getName());
		this.model = model;
		this.indexCall = indexCall;
		templateList = model.getName() + "List";
		templateForm = model.getName() + "Form";
		templateShow = model.getName() + "Show";
		if (log.isDebugEnabled())
			log.debug("DynamicCrudController for model : " + model);
	}

	@Override
	protected String templateForList() {
		return templateList;
	}

	@Override
	protected String templateForForm() {
		return templateForm;
	}

	@Override
	protected String templateForShow() {
		return templateShow;
	}

	@Override
	protected Call toIndex() {
		return indexCall;
	}

	@Override
	public Result read(Object key) {
		return show(key);
	}

	@Override
	public Result list() {
		return list(0);
	}
	
	@Override
	protected Content render(String template, Parameters params) {
		Content content;
		try {
			content = call("views.html." + template, "render", params);
		} catch (ClassNotFoundException | MethodNotFoundException e) {
			if (log.isDebugEnabled())
				log.debug("template not found : '" + template + "'");
			throw new TemplateNotFoundException();
		}
		return content;
	}

	@Override
	protected Content renderList(Page p) {
		try {
			return super.renderList(p);
		} catch (TemplateNotFoundException e) {
			// use dynamic template
			if (log.isDebugEnabled())
				log.debug("Rendering dynamic LIST template for model : " + model);
			return play.utils.crud.views.html.list.render(model, model.getFields().values(), p);
		}
	}

	protected Content renderForm(Object key, Form form) {
		if (log.isDebugEnabled())
			log.debug("renderForm <- form:" + form);
		try {
			return super.renderForm(key, form);
		} catch (TemplateNotFoundException e) {
			// use dynamic template
			if (log.isDebugEnabled())
				log.debug("Rendering dynamic FORM template for model : " + model);
			return play.utils.crud.views.html.edit.render(model, model.getFields().values(), key, form);
		}
	}

	@Override
	protected Content renderShow(BasicModel modelObject) {
		try {
			return super.renderShow(modelObject);
		} catch (TemplateNotFoundException e) {
			// use dynamic template
			if (log.isDebugEnabled())
				log.debug("Rendering dynamic SHOW template for model : " + model);
			return play.utils.crud.views.html.show.render(model, model.getFields().values(), modelObject);
		}
	}
	
	private class TemplateNotFoundException extends RuntimeException {
		/** serial id */
		private static final long serialVersionUID = 1L;
	}

}
