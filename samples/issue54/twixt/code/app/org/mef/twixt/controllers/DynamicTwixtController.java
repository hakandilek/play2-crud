package org.mef.twixt.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;



import org.mef.twixt.*;
import org.mef.twixt.widget.MySelectWidget;
import org.springframework.util.ReflectionUtils;

import play.Logger;
import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.CheckboxWidget;
import play.utils.meta.form.DateWidget;
import play.utils.meta.form.FileWidget;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.NumberWidget;
import play.utils.meta.form.SelectWidget;
import play.utils.meta.form.TextWidget;

public abstract class DynamicTwixtController<K,  M extends BasicModel<K>,T extends ValueContainer> extends TwixtController<K, M,T> implements ReflectionUtils.FieldCallback, ReflectionUtils.FieldFilter
{
	private class PreRender implements  ReflectionUtils.FieldCallback
	{
		@Override
		public void doWith(Field arg0) throws IllegalArgumentException, IllegalAccessException 
		{
			Class<?> clazz = arg0.getType();
			if (LongSelectValue.class.isAssignableFrom(clazz))
			{
				LongSelectValue val = (LongSelectValue) arg0.get(preRenderTwixt);
				FieldMetadata meta = findMeta(arg0);
				MySelectWidget w = (MySelectWidget) meta.getWidget();
				
				w.options = new TreeMap<Object,String>();
				for(Long key : val.options().keySet())
				{
					w.options.put(key.toString(), val.options().get(key)); //put key as string because form.value will be string
				}
			}
			else if (SelectValue.class.isAssignableFrom(clazz))
			{
				SelectValue val = (SelectValue) arg0.get(preRenderTwixt);
				FieldMetadata meta = findMeta(arg0);
				MySelectWidget w = (MySelectWidget) meta.getWidget();
				
				w.options = new TreeMap<Object,String>();
				for(String key : val.options().keySet())
				{
					w.options.put(key, val.options().get(key));
				}
			}
		}

		private FieldMetadata findMeta(Field arg0) 
		{
			for(FieldMetadata meta : metaL)
			{
				if (arg0.getName().equals(meta.getField().getName()))
				{
					return meta;
				}
			}
			return null;
		}
	}


	List<FieldMetadata> metaL = new ArrayList<FieldMetadata>();
	private T preRenderTwixt;

	public DynamicTwixtController(DAO<K, M> dao, Class<K> keyClass, Class<M> modelClass, Class<T>twixtClass, int pageSize, String orderBy) 
	{
		super(dao, keyClass, modelClass, twixtClass, pageSize, orderBy);
		ReflectionUtils.doWithFields(twixtClass, this, this);
	}

	@Override
	public void doWith(Field field) throws IllegalArgumentException,
	IllegalAccessException 
	{
		addFieldToMetaL(field);
	}

	@Override
	public boolean matches(Field arg0) 
	{
		return (Value.class.isAssignableFrom(arg0.getType()));
	}	

	private void addFieldToMetaL(Field f)
	{
		FieldMetadata meta = new FieldMetadata(f, null); //new StringConverter());
		FormFieldWidget w = createWidget(f, meta);
		try {
			forceSetWidget(meta, w);
			Logger.info("ffff: " + f.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		metaL.add(meta);
	}

	protected FormFieldWidget createWidget(Field f, FieldMetadata meta)
	{
		Class<?> clazz = f.getType();

		if (clazz.equals(IntegerValue.class))
		{
			return new NumberWidget(meta);
		}
		else if (clazz.equals(BooleanValue.class))
		{
			return new CheckboxWidget(meta);
		}
		else if (clazz.equals(DateValue.class))
		{
			return new DateWidget(meta); //yyyy-mm-dd
		}
		else if (clazz.isAssignableFrom(SelectValue.class))
		{
			return new MySelectWidget(meta);
		}
		else if (LongSelectValue.class.isAssignableFrom(clazz))
		{
			return new MySelectWidget(meta);
		}
		else if (FileValue.class.isAssignableFrom(clazz))
		{
			return new FileWidget(meta);
		}
		else
		{
			TextWidget w = new TextWidget(meta);
			return w;
		}
	}

	private void forceSetWidget(FieldMetadata meta2, FormFieldWidget w) throws Exception 
	{
//		Field f = ReflectionUtils.findField(meta2.getClass(), "widget");
//		f.setAccessible(true); //force!
//		f.set(meta2, w);
		meta2.setWidget(w);
	}

	@Override
	protected Content xrenderForm(K key, Form<T> form) 
	{
		//need to assign options map to any selectvalues
		preRenderTwixt = form.get();
		ReflectionUtils.doWithFields(preRenderTwixt.getClass(), new PreRender(), ReflectionUtils.COPYABLE_FIELDS);

		return render(templateForForm(), with(getKeyClass(), key).and(Form.class, form).and(metaL.getClass(), metaL));
	}	



}
