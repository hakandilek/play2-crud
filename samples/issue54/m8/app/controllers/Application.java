package controllers;

import org.mef.twixt.binder.TwixtForm;
import org.mef.twixt.validate.ValContext;

import models.Taxi;
import play.Logger;
import play.api.Play;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	public static Result index() {
		
		ClassLoader appClassloader = Play.classloader(Play.current());
		
		Taxi obj = new Taxi();
		ClassLoader libClassloader2 = obj.getClass().getClassLoader();	
		
		ValContext vtx = new ValContext();
		ClassLoader libClassloader3 = obj.getClass().getClassLoader();	
		
		Logger.info("A: " + appClassloader.getClass().getName());
		Logger.info("B: " + libClassloader2.getClass().getName());
		Logger.info("C: " + libClassloader3.getClass().getName());
		
		return ok(views.html.index.render());
	}

}