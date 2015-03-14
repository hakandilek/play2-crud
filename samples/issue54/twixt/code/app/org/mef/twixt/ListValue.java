package org.mef.twixt;

import java.util.ArrayList;
import java.util.List;

import org.mef.twixt.validate.ValContext;


public class ListValue extends Value
{
	public ListValue()
	{
		this(new ArrayList<Value>());
	}
	public ListValue(List<Value> L)
	{
		super(L);
	}

	@Override
	protected String render()
	{
		return null; //!!what should we do
	}

	@Override
	protected void parse(String input)
	{ //!!
	}

	//return in our type
	public List<Value> get()
	{
		//should we return a copy??
		List<Value> L = (List<Value>)obj;
		return L;
	}
	public void set(List<Value> L)
	{
		setUnderlyingValue(L);
	}
	
	public void addElement(Value val)
	{
		List<Value> L = get();
		L.add(val);
	}

	public Value getIth(int index) 
	{
		List<Value> L = get();
		return L.get(index);
	}

	public Object size() 
	{
		List<Value> L = get();
		return L.size();
	}	
	
	
	@Override
	public void validate(ValContext valctx)
	{
		if (validator != null)
		{
			validator.validate(valctx, this);
			return;
		}
		
		for(Value val : get())
		{
			val.validate(valctx);
		}
	}	
}
