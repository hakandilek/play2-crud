package mef.validate;
import org.mef.twixt.StringValue;
import org.mef.twixt.ValueContainer;
import org.mef.twixt.validate.ValContext;

import models.Sample;


public class SampleTwixt implements ValueContainer
{
	public StringValue name;
	
	public SampleTwixt()
	{
		this("");
	}
	
	public SampleTwixt(String namex)
	{
		this.name = new StringValue(namex);
	}

	@Override
	public void validate(ValContext arg0) 
	{
		name.validate(arg0);
	}
	
	@Override 
	public void copyTo(Object model)
	{
		Sample m = (Sample)model;
		m.setName(this.name.get());
	}

	@Override 
	public void copyFrom(Object model)
	{
		Sample m = (Sample)model;
		this.name.set(m.getName());
	}
}
