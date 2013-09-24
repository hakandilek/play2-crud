package controllers;

import static play.data.Form.*;

import javax.inject.Inject;

import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;

public class SampleController extends CRUDController<Long, Sample> {
	
	@Inject
	public SampleController(SampleDAO dao) {
		super(dao, form(Sample.class), Long.class, Sample.class, 10, "name");
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

	@Override
	public Result read(Long key) {
		return show(key);
	}

	@Override
	public Result list() {
		return list(0);
	}

}
