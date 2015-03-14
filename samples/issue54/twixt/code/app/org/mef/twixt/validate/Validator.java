package org.mef.twixt.validate;

import org.mef.twixt.Value;



public interface Validator
{
	void validate(ValContext valctx, Value value);
}