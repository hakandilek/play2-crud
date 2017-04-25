package org.mef.twixt;

import org.mef.twixt.validate.ValContext;


public interface ValueContainer
{
	void validate(ValContext vtx);
	void copyTo(Object model);
	void copyFrom(Object model);
}