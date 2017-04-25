package org.mef.twixt.binder;

import java.lang.reflect.Field;

import org.mef.twixt.*;
import org.mef.twixt.validate.ValContext;
import org.springframework.util.ReflectionUtils;

public abstract class TwixtForm implements ValueContainer
{
	private class Facade implements  FormCopier.FieldCopier, ReflectionUtils.FieldCallback
	{
		@Override
		public void doWith(Field field)
		{
			Class<?> clazz = field.getType();
			if (Value.class.isAssignableFrom(clazz))
			{
				try 
				{
					field.setAccessible(true);
					if (field.get(TwixtForm.this) != null)
					{
						return; //skip ones that are already not null
					}
					
					Object obj = clazz.newInstance();
					field.set(TwixtForm.this, obj);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void copyFieldFromModel(FormCopier copier, Field field)
		{
			copier.copyFieldFromModel(field);
		}
		@Override
		public void copyFieldToModel(FormCopier copier, Field field)
		{
			copier.copyFieldToModel(field);
		}
	}
	
	private class ValidationFacade implements  ReflectionUtils.FieldCallback
	{
		private ValContext valctx;

		public ValidationFacade(ValContext valctx)
		{
			this.valctx = valctx;
		}

		@Override
		public void doWith(Field field)
		{
			Class<?> clazz = field.getType();
			if (Value.class.isAssignableFrom(clazz))
			{
				try 
				{
					field.setAccessible(true);
					Value value = (Value) field.get(TwixtForm.this);
					
					if (value != null)
					{
						valctx.setCurrentItemName(field.getName());
						value.validate(valctx);
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private Facade _facade = new Facade(); //avoid name clash, use _
	
	public TwixtForm()
	{}

	protected void initFields()
	{
		ReflectionUtils.doWithFields(this.getClass(), _facade, ReflectionUtils.COPYABLE_FIELDS);
	}

	
	@Override
	public void copyFrom(Object model) 
	{
		this.copyFieldsFromModel(model);
	}

	@Override
	public void copyTo(Object model) 
	{
		this.copyFieldsToModel(model);
	}
	
	protected void copyFieldsFromModel(Object model, String... fieldsToNotCopy) 
	{
		FormCopier copier = new FormCopier(_facade);
		copier.copyFromModel(model, this, fieldsToNotCopy);
	}
	protected void copyFieldsToModel(Object model, String... fieldsToNotCopy) 
	{
		FormCopier copier = new FormCopier(_facade);
		copier.copyToModel(this, model, fieldsToNotCopy);
	}
	

	@Override
	public void validate(ValContext valctx) 
	{
		//use reflection so we can set itemName for each Value
		ValidationFacade valfacade = new ValidationFacade(valctx);
		ReflectionUtils.doWithFields(this.getClass(), valfacade, ReflectionUtils.COPYABLE_FIELDS);
	}
	
}
