package play.utils.crud;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.utils.inject.InjectAdapter;
import play.utils.meta.ClasspathScanningControllerRegistry;
import play.utils.meta.ClasspathScanningModelRegistry;
import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.ModelRegistry;

public class CrudManager {

	private static ALogger log = Logger.of(CrudManager.class);

	DynamicRestController restController;

	GlobalSettings global;

	InjectAdapter injections = InjectAdapter.getInstance();

	public CrudManager(GlobalSettings global) {
		this.global = global;
	}

	public void initialize(Application app) {
		if (log.isDebugEnabled())
			log.debug("initialize <-");
		CrudControllerRegistry crudControllers = new ClasspathScanningControllerRegistry(app, global);
		ModelRegistry models = new ClasspathScanningModelRegistry(app);
		restController = new DynamicRestController(crudControllers, models);
	}

	@SuppressWarnings("unchecked")
	public <A> A getController(Class<A> type) throws Exception {
		if (DynamicRestController.class.equals(type))
			return (A) restController;
		
		return injections.getInstance(type);
	}

}
