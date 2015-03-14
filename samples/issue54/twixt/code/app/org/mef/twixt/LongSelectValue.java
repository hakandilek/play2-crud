package org.mef.twixt;

import java.util.Map;

import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;


public class LongSelectValue extends LongValue
{
	private class SelectValidator implements Validator
	{
		@Override
		public void validate(ValContext valctx, Value value) 
		{
			if (options == null)
			{
				return;
			}

			LongValue lval = (LongValue)value;
			Long tmp = lval.get();
			boolean b = options.containsKey(tmp);
			if (!b)
			{
				valctx.addError("select: unknown id: " + tmp.toString());
			}
		}
	}

	protected Map<Long, String> options;

	public LongSelectValue()
	{
		this(0L, null);
	}
	public LongSelectValue(Long id) 
	{
		this(id, null);
	}
	public LongSelectValue(Long id, Map<Long,String> options) 
	{
		super(id);
		this.options = options;
		setValidator(new SelectValidator());
	}


	public Map<Long,String> options()
	{
		return options;
	}
	public void setOptions(Map<Long,String> map)
	{
		options = map;
	}
}
