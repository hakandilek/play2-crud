package org.mef.twixt.controllers;

import static play.data.Form.form;

import javax.inject.Inject;

import org.mef.twixt.ValueContainer;



import org.mef.twixt.binder.TwixtBinder;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.twirl.api.Content;
import play.utils.crud.CRUDController;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;

public abstract class TwixtController<K,  M extends BasicModel<K>,T extends ValueContainer> extends CRUDController<K, M> 
{
	protected Class<T> twixtClass;
	
	@Inject
	public TwixtController(DAO<K, M> dao, Class<K> keyClass, Class<M> modelClass, Class<T>twixtClass, int pageSize, String orderBy) {
		super(dao, form(modelClass), keyClass, modelClass, pageSize, orderBy);
		this.twixtClass = twixtClass;
	}

	private static ALogger xlog = Logger.of(CRUDController.class);
	
	protected Content xrenderForm(K key, Form<T> form) 
	{
		return render(templateForForm(), with(getKeyClass(), key).and(Form.class, form));
	}
	
	
	public Result newForm() {
		if (xlog.isDebugEnabled())
			xlog.debug("xnewForm() <-");
		Form<T> form =  createForm(null);

		return ok(xrenderForm(null, form));
	}	
	
	private Form<T> createForm(M model)
	{
		T twixt = createTwixt();
		if (model != null)
		{
			twixt.copyFrom(model);
		}
		Form<T> frm = Form.form(this.twixtClass).fill(twixt);
		return frm;
		
	}
	
	@Override
	public Result create()
	{
		if (xlog.isDebugEnabled())
			xlog.debug("ccXcreate() <-");
		
		TwixtBinder<T> binder = new TwixtBinder<T>(twixtClass);
		boolean b = binder.bind();
		Form<T> filledForm = binder.getForm();
		
		if (!b) {
			if (xlog.isDebugEnabled())
				xlog.debug("Xvalidation errors occured: " + binder.getValidationErrors()); // filledForm.errors());

			return badRequest(xrenderForm(null, filledForm));
		} else {
			T twixt = filledForm.get();
			M model = createModel();
			if (model == null)
			{
				return badRequest(xrenderForm(null, filledForm));
			}

			twixt.copyTo(model);
			
			DAO<K,M> dao = getDao();
			dao.create(model);
			if (xlog.isDebugEnabled())
				xlog.debug("Xentity created");

			Call index = toIndex();
			if (xlog.isDebugEnabled())
				xlog.debug("Xindex : " + index);
			return redirect(index);
		}
	}
		
	public Result editForm(K key) {
		if (xlog.isDebugEnabled())
			xlog.debug("editForm() <-" + key);

		M model = this.getDao().get(key);
		if (xlog.isDebugEnabled())
			xlog.debug("model : " + model);

		Form<T> frm = createForm(model);
		return ok(xrenderForm(key, frm));
	}

	public Result update(K key) {
		if (xlog.isDebugEnabled())
			xlog.debug("update() <-" + key);

		M model = getDao().get(key);
		T twixt = createTwixt();
		twixt.copyFrom(model);
		
		TwixtBinder<T> binder = new TwixtBinder<T>(twixtClass, twixt);
		boolean b = binder.bind();
		Form<T> filledForm = binder.getForm();
		
		if (! b) {
			if (xlog.isDebugEnabled())
				xlog.debug("validation errors occured: " + binder.getValidationErrors());

			return badRequest(xrenderForm(key, filledForm));
		} else {
			twixt = filledForm.get();		
			twixt.copyTo(model);
			if (xlog.isDebugEnabled())
				xlog.debug("model : " + model);
			getDao().update(model);
			if (xlog.isDebugEnabled())
				xlog.debug("entity updated");

			Call index = toIndex();
			if (xlog.isDebugEnabled())
				xlog.debug("index : " + index);
			return redirect(index);
		}
	}
	
	
	protected M createModel()
	{
		M model = null;
		try {
			model = this.getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	protected T createTwixt()
	{
		T twixt = null;
		try {
			twixt = this.twixtClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return twixt;
	}
	
	protected String getModelTemplatePrefix()
	{
		return this.getModelClass().getSimpleName();
	}
	protected String genTemplate(String s)
	{
		s = getModelTemplatePrefix() + s;
		
		String tmp = s.substring(0, 1).toLowerCase();
		tmp += s.substring(1);
		
		return tmp;
	}
	
	@Override
	protected String templateForList() {
		return  genTemplate("List");
	}

	@Override
	protected String templateForForm() {
		return  genTemplate("Form");
	}

	@Override
	protected String templateForShow() {
		return  genTemplate("Show");
	}
	

	
}
