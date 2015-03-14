package org.mef.twixt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValue extends Value
{
	public DateValue()
	{
		this(new Date());
	}
	public DateValue(Date n)
	{
		super(n);
	}

	@Override
	protected String render()
	{
		Date dt = get();
		//default rendering is the same as HTML5 date input: yyyy-mm-dd
		SimpleDateFormat dateFormat = createFormatter();
		String s = dateFormat.format(dt);
		return s;
	}

	@Override
	//input has not yet been validated so parsing may fail and throw an exception
	protected void parse(String input) throws Exception 
	{
		SimpleDateFormat dateFormat = createFormatter();
		Date dt = dateFormat.parse(input);
		this.setUnderlyingValue(dt);
	}

	private SimpleDateFormat createFormatter()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat;
	}

	//return in our type
	public Date get()
	{
		Date nVal = (Date)obj;
		return nVal;
	}
	public void set(Date dt)
	{
		setUnderlyingValue(dt);
	}
}
