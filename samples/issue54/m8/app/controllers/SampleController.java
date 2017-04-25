package controllers;

import javax.inject.Inject;

import org.mef.twixt.controllers.TwixtController;

import mef.validate.SampleTwixt;
import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Call;

public class SampleController extends TwixtController<Long, Sample, SampleTwixt> {
	
	@Inject
	public SampleController(SampleDAO dao) 
	{
		super(dao, Long.class, Sample.class, SampleTwixt.class, 10, "name");
	}


	@Override
	protected Call toIndex() {
		return routes.Application.index();
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
	
}
