package org.mef.twixt.validators;
import org.mef.twixt.Value;
import org.mef.twixt.validate.ErrorMessages;
import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;


public class NotEmptyStringValidator implements Validator
{
	@Override
	public void validate(ValContext valctx, Value obj) 
	{
		String s = obj.toString();
		if (s == null || s.isEmpty())
		{
			valctx.addError(ErrorMessages.NOTEMPTY);
		}
	}
}