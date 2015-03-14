package org.mef.twixt;
//package org.mef.framework.metadata;
//import java.util.HashMap;
//
//import org.mef.framework.metadata.validate.ValContext;
//
//
//public class TupleValue implements ValueContainer
//{
//	private HashMap<String, Value> map;
//	
//	public TupleValue()
//	{
//		map = new HashMap<String, Value>();
//	}
//	
//	public TupleValue(TupleValue src)
//	{
//		map = new HashMap<String, Value>();
//		for(String fieldName : src.map.keySet())
//		{
//			Value val = src.map.get(fieldName);
//			Value copy = new Value(val);
//			map.put(fieldName, copy);
//		}
//	}
//	
//	public void addField(String fieldName, Value val)
//	{
//		map.put(fieldName, val);
//	}
//
//	public Value field(String fieldName) 
//	{
//		Value field = map.get(fieldName);
//		return field;
//	}
//	
//	//validation
//	public void validateContainer(ValContext vtx)
//	{
//		for(String fieldName : map.keySet())
//		{
//			Value val = map.get(fieldName);
//			vtx.validate(val);
//		}
//	}
//
//	@Override
//	public void copyTo(Object model) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void copyFrom(Object model) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	//how handle toString?
//	
//}