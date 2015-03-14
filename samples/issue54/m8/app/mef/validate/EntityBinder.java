package mef.validate;


import org.mef.framework.binder.IFormBinder;
import org.mef.framework.entities.Entity;

import play.data.Form;

public class EntityBinder<T extends Entity> implements IFormBinder<T>
{
	private Form<T> filledForm;
	private T entity;
	private Class<T> clazz;

	public EntityBinder(Class<T> clazz)
	{
		this.clazz = clazz;
	}
	@Override
	public boolean bind() 
	{
		this.filledForm = Form.form(clazz).bindFromRequest();
		entity = this.filledForm.get();
		return ! filledForm.hasErrors();
	}

	@Override
	public T get() 
	{
		return entity;
	}

	@Override
	public Object getValidationErrors() 
	{
		return filledForm.errors();
	}

	@Override
	public Form<T> getForm()
	{
		return filledForm;
	}
	@Override
	public Form<T> fillForm(T input) 
	{
		Form<T> frm = (Form<T>) Form.form(input.getClass());
		frm = frm.fill(input);
		return frm;
	}
}
