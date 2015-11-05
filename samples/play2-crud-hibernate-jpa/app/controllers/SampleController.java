package controllers;

import static play.data.Form.*;

import javax.inject.Inject;

import models.Sample;
import models.dao.SampleDAO;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Call;
import play.mvc.Result;
import play.mvc.Results;
import play.utils.crud.CRUDController;
import play.utils.crud.routes;

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
		return routes.RouterCrudController.index();
	}

	@Transactional(readOnly=true)
	@Override
	public Result list(int page) {
		try {
			return JPA.withTransaction(() -> SampleController.super.list(page));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Results.internalServerError();
		}
	}

	@Transactional
	@Override
	public Result create() {
		try {
			return JPA.withTransaction(() -> SampleController.super.create());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Results.internalServerError();
		}
	}

	@Transactional
	@Override
	public Result update(Long key) {
		try {
			return JPA.withTransaction(() -> SampleController.super.update(key));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Results.internalServerError();
		}
	}

	@Transactional(readOnly=true)
	@Override
	public Result show(Long key) {
		try {
			return JPA.withTransaction(() -> SampleController.super.show(key));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Results.internalServerError();
		}
	}

	@Transactional
	@Override
	public Result delete(Long key) {
		try {
			return JPA.withTransaction(() -> SampleController.super.delete(key));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Results.internalServerError();
		}
	}
	
}
