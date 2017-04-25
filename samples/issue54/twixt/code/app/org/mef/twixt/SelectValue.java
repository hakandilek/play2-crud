package org.mef.twixt;

import java.util.Map;

import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;


public class SelectValue extends StringValue
{
	class SelectValidator implements Validator
	{
		@Override
		public void validate(ValContext valctx, Value value) 
		{
			if (options == null)
			{
				return;
			}

			String id = (String)value.toString();
			boolean b = options.containsKey(id);
			if (!b)
			{
				valctx.addError("select: unknown id: " + id);
			}
		}
	}

	protected Map<String, String> options;

	public SelectValue()
	{
		this("", null);
	}
	public SelectValue(String id)
	{
		this(id, null);
	}

	public SelectValue(String id, Map<String,String> options) 
	{
		super(id);
		this.options = options;
		setValidator(new SelectValidator());
	}


	public Map<String,String> options()
	{
		return options;
	}
	public void setOptions(Map<String,String> map)
	{
		options = map;
	}
}
