package controllers;

import models.dao.SampleDAO;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	private static SampleDAO sampleDAO = new SampleDAO();
	private static SampleController sampleController = new SampleController(sampleDAO);
	private static SampleRestController restController = new SampleRestController(sampleDAO);

	public static Result index() {
		return ok(views.html.index.render());
	}

	public static Result sampleList() {
		return sampleController.list(0);
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

	public static Result restList() {
		return restController.list();
	}

	public static Result restCreate() {
		return restController.create();
	}

	public static Result restUpdate(Long key) {
		return restController.update(key);
	}

	public static Result restDelete(Long key) {
		return restController.delete(key);
	}

	public static Result restGet(Long key) {
		return restController.get(key);
	}

}