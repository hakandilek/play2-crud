package controllers;

import java.lang.reflect.Field;

import javax.inject.Inject;



import org.mef.twixt.controllers.DynamicTwixtController;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;
import play.mvc.Call;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.TextAreaWidget;

public class TaxiController extends MyDynamicTwixtController<Long, Taxi, TaxiTwixt> 
{
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
		templatePackageName = "views.html.taxi.";
	}
	
	@Override
	protected FormFieldWidget createWidget(Field f, FieldMetadata meta)
	{
		if (f.getName().equals("name"))
		{
			TextAreaWidget w = new TextAreaWidget(meta);
			return w;
		}
		else
		{
			return super.createWidget(f, meta);
		}
	}
	
	
	@Override
	protected String templateForList() {
		return  genTemplate("List");
	}

	@Override
	protected String templateForForm() {
		return  genTemplate("Form");
	}

	@Override
	protected String templateForShow() {
		return  genTemplate("Show");
	}
	

	
	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
