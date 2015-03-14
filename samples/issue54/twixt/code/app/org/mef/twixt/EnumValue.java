package org.mef.twixt;
//package org.mef.framework.metadata;
//
//import org.mef.framework.metadata.validate.ValidationErrors;
//
//
//public abstract class EnumValue extends IntegerValueAndValidator
//{
//	public EnumValue()
//	{
//		this(0, ""); //oops, how get itemName!!
//	}
//	public EnumValue(int val, String itemName)
//	{
//		super(val, itemName);
//	}
//	
//	@Override
//	public boolean validate(Object val, ValidationErrors errors) 
//	{
//		int n = (Integer)val;
//		if (! onValidate(n))
//		{
//			errors.addError(String.format("%d is not a valid enum value", n));
//			return false;
//		}
//		return true;
//	}
//
//	protected abstract boolean onValidate(int val);
//	
//	//how handle toString()??
//}