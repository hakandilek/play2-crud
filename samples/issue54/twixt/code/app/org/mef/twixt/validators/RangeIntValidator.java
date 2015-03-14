package org.mef.twixt.validators;

import org.mef.twixt.IntegerValue;
import org.mef.twixt.Value;
import org.mef.twixt.validate.ErrorMessages;
import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;


public class RangeIntValidator implements Validator
{
	private int min;
	private int max;
	
	public RangeIntValidator(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
	@Override
	public void validate(ValContext valctx, Value obj) 
	{
		IntegerValue value = (IntegerValue) obj;
		int n = value.get();
		
		boolean ok = (n >= min && n <= max);
		if (! ok)
		{
//			errors.addError(String.format("value %d not in range %d to %d", n, min, max));
			valctx.addError(ErrorMessages.RANGE_INT, n, min, max);
		}
	}
}