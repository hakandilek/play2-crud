package controllers;

import javax.inject.Inject;

import models.Simple;
import models.dao.SimpleDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import static play.data.Form.*;

public class SimpleController extends CRUDController<Long, Simple> {
	@Inject
	public SimpleController(SimpleDAO dao) {
		super(dao, form(Simple.class), Long.class, Simple.class, 10, "name");
	}

	protected String templateForList() {
		return "sampleList";
	}

	protected String templateForForm() {
		return "sampleForm";
	}

	protected String templateForShow() {
		return "sampleShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
}