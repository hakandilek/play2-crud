package mef.validate;

import org.mef.framework.binder.IFormBinder;

import play.data.Form;

//very simple binder that just returns its object. some methods not implemented
public class MockBinder<T> implements IFormBinder<T>
{
	private T obj;
	private boolean isValid;

	public MockBinder(T input)
	{
		this(input, true);
	}
	public MockBinder(T input, boolean isValid)
	{
		this.obj = input;
		this.isValid = isValid;
	}
	
	@Override
	public boolean bind() 
	{
		return isValid;
	}

	@Override
	public T get()
	{
		return obj;
	}

	@Override
	public Object getValidationErrors() 
	{
		return null;
	}
	@Override
	public Form<T> getForm() {
		return null;
	}
	@Override
	public Form<T> fillForm(T input) {
		return null;
	}

}
