package controllers;

import javax.inject.Inject;

import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Call;

public class SampleController extends MyCRUDController<Long, Sample> {
	
	@Inject
	public SampleController(SampleDAO dao) {
		super(dao, Long.class, Sample.class);
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
		return routes.Application.index();
	}

}
