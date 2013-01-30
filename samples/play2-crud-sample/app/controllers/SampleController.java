package controllers;

import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class SampleController extends CRUDController<Long, Sample> {
	
	public SampleController(SampleDAO dao) {
		super(dao, form(Sample.class), Long.class, Sample.class);
	}

	@Override
	protected String templateForList() {
		return "sampleList";
	}

	@Override
	protected String templateForForm() {
		return "sampleForm";
	}

	@Override
	protected String templateForShow() {
		return "sampleShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Application.sampleList();
	}

}
