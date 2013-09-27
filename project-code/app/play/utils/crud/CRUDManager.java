package play.utils.crud;

import com.google.common.collect.ImmutableSortedSet;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.utils.inject.InjectAdapter;
import play.utils.meta.ClasspathScanningControllerRegistry;
import play.utils.meta.ClasspathScanningConverterRegistry;
import play.utils.meta.ClasspathScanningModelRegistry;
import play.utils.meta.ConverterRegistry;
import play.utils.meta.CrudControllerRegistry;

public class CRUDManager {

	private static ALogger log = Logger.of(CRUDManager.class);

	GlobalSettings global;

	InjectAdapter injections = InjectAdapter.getInstance();

	RouterRestController dynamicRestController;

	RouterCrudController dynamicCrudController;

	ClasspathScanningModelRegistry models;
	
	private static CRUDManager instance;

	public CRUDManager(GlobalSettings global) {
		this.global = global;
	}

	public void initialize(Application app) {
		if (log.isDebugEnabled())
			log.debug("initialize <-");
		ConverterRegistry converters = new ClasspathScanningConverterRegistry(app);
		models = new ClasspathScanningModelRegistry(app, converters);
		CrudControllerRegistry crudControllers = new ClasspathScanningControllerRegistry(app, global, models);
		dynamicRestController = new RouterRestController(crudControllers, models);
		dynamicCrudController = new RouterCrudController(app.classloader(), crudControllers, models);
		instance = this;
	}

	@SuppressWarnings("unchecked")
	public <A> A getController(Class<A> type) throws Exception {
		if (RouterRestController.class.equals(type))
			return (A) dynamicRestController;

		if (RouterCrudController.class.equals(type))
			return (A) dynamicCrudController;

		return injections.getInstance(type);
	}

    public static CRUDManager getInstance() {
		return instance;
	}

	public Iterable<String> modelNames() {
        return ImmutableSortedSet.<String>naturalOrder().addAll(models.getModelNames()).build();
    }

}
