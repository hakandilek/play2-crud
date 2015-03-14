package mef.validate;

import java.util.HashMap;
import java.util.Map;

import org.mef.twixt.LongSelectValue;


public class AccountType extends LongSelectValue
{
	public AccountType()
	{
		this(1L);
	}
	public AccountType(Long id) 
	{
		super(id, accountTypes());
	}

	private static Map<Long,String> accountTypes()
	{
		Map<Long,String> map = new HashMap<Long,String>();
		map.put(1L, "normal");
		map.put(2L, "premium");
		map.put(3L, "executive");
		return map;
	}		
}