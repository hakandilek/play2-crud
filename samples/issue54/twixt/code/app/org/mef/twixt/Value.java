package org.mef.twixt;

import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;


public abstract class Value
{
	protected Object obj;
	protected Converter converter;
	protected Validator validator;

	public Value()
	{}
	public Value(Object obj)
	{
		this.obj = obj;
	}

	//??deep copy needed!!

	public Object getUnderlyingValue()
	{
		return obj;
	}
	public void setUnderlyingValue(Object obj)
	{
		this.obj = obj;
	}

	public void validate(ValContext valctx)
	{
		if (validator != null)
		{
			validator.validate(valctx, this);
		}
	}

	protected abstract void parse(String input) throws Exception;
	protected abstract String render();
	@Override
	public String toString()
	{
		if (converter != null)
		{
			return converter.print(obj);
		}
		else
		{
			return render();
		}
	}
	public void fromString(String input) throws Exception
	{
		if (converter != null)
		{
			Object object = converter.parse(input);
			setUnderlyingValue(object);
		}
		else
		{
			parse(input);
		}
	}
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}