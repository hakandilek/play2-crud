package org.mef.twixt.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ValContext
{
	private int failCount;
    private Map<String,List<ValidationErrorSpec>> mapErrors;
    private String currentItemName;
	
	public ValContext()
	{
		mapErrors = new HashMap<String,List<ValidationErrorSpec>>();
	}
	
	public void setCurrentItemName(String itemName)
	{
		currentItemName = itemName;
	}
	
	public void addError(String fmt, Object...strings)
	{
		ValidationErrors errors = new ValidationErrors();
		errors.map = mapErrors;
		errors.setItemName(currentItemName);
		
		errors.addError(fmt, strings);
		failCount++;
	}
	
	public int getFailCount()
	{
		return failCount;
	}
	
    public Map<String,List<ValidationErrorSpec>> getErrors()
    {
    	return mapErrors;
    }
    
    public List<ValidationErrorSpec> getFlattendErrorList()
    {
    	List<ValidationErrorSpec> resultL = new ArrayList<ValidationErrorSpec>();
    	
		for(String key : mapErrors.keySet())
		{
			List<ValidationErrorSpec> L = mapErrors.get(key);
			for(ValidationErrorSpec spec : L)
			{
				resultL.add(spec);
			}
		}
		return resultL;
    }

	public Object succeeded() 
	{
		return failCount == 0;
	}
}