package org.mef.twixt.binder;

import play.data.Form;


public interface IFormBinder<T>
{
	boolean bind();

	Object getValidationErrors();

	T get(); //return even if bind failed. may be partially filled
	Form<T> getForm();
	
	Form<T> fillForm(T input);
}