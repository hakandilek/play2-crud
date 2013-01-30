package controllers;

import models.dao.SampleDAO;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	private static SampleDAO sampleDAO = new SampleDAO();
	private static SampleController sampleController = new SampleController(sampleDAO);

	public static Result index() {
		return ok(views.html.index.render());
	}

	public static Result sampleList() {
		return sampleController.listAll();
	}

	public static Result sampleNewForm() {
		return sampleController.newForm();
	}

	public static Result sampleCreate() {
		return sampleController.create();
	}

	public static Result sampleEditForm(Long key) {
		return sampleController.editForm(key);
	}

	public static Result sampleUpdate(Long key) {
		return sampleController.update(key);
	}

	public static Result sampleDelete(Long key) {
		return sampleController.delete(key);
	}

	public static Result sampleShow(Long key) {
		return sampleController.show(key);
	}

}