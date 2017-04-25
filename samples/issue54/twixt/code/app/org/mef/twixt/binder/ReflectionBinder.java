package org.mef.twixt.binder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mef.twixt.*;
import org.mef.twixt.validate.*;
import org.reflections.Reflections;

public class ReflectionBinder
{
	ValContext vtx;
	private Field fieldBeingParsed;

	public ReflectionBinder()
	{
		vtx = new ValContext();
	}

	public Map<String,List<ValidationErrorSpec>> getErrors()
	{
		return vtx.getErrors();
	}
	public ValContext getContext()
	{
		return vtx;
	}

	boolean bind(ValueContainer input, Map<String,String> map) 
	{
		boolean ok = false;

		try {
			ok = bindImpl(input, map);
		} catch (Exception e) {
			e.printStackTrace();
			if (fieldBeingParsed != null) //failed in fromString?
			{
				vtx.setCurrentItemName(fieldBeingParsed.getName());				
				vtx.addError(String.format("%s: invalid input", this.fieldBeingParsed.getName()));
			}
		}

		//and validate
		if (ok)
		{
			//TODO if validateContainer exists call it, else use reflection
			input.validate(vtx);

			ok = (vtx.getFailCount() == 0);
		}

		return ok;
	}

	boolean bindImpl(ValueContainer input, Map<String,String> map) throws Exception
	{
		boolean ok = true;
		Set<Field> list = Reflections.getAllFields(input.getClass(), Reflections.withModifier(Modifier.PUBLIC));
		for(Field fld : list)
		{
			String fieldName = fld.getName();

			String s = map.get(fieldName);
			if (s != null)
			{
				Object obj = fld.get(input);
				if (obj instanceof Value)
				{
					Value val = (Value)obj;
					fieldBeingParsed = fld;
					val.fromString(s);
					fieldBeingParsed = null;
				}
			}
		}

		return ok;
	}
}