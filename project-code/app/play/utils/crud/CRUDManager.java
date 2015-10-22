package play.utils.crud;

import com.google.common.collect.ImmutableSortedSet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.api.Play;
import play.utils.meta.ConverterRegistry;
import play.utils.meta.ControllerRegistry;
import play.utils.meta.cp.ClasspathScanningControllerRegistry;
import play.utils.meta.cp.ClasspathScanningConverterRegistry;
import play.utils.meta.cp.ClasspathScanningModelRegistry;

public class CRUDManager {

	private static ALogger log = Logger.of(CRUDManager.class);

	GlobalSettings global;

	AnnotationConfigApplicationContext ctx;

	RouterRestController dynamicRestController;

	RouterCrudController dynamicCrudController;

	ClasspathScanningModelRegistry models;
	
	private static CRUDManager instance;

	public CRUDManager(GlobalSettings global, AnnotationConfigApplicationContext ctx) {

		this.global = global;
		this.ctx = ctx;
	}

	public void initialize(Application app) {
		if (log.isDebugEnabled())
			log.debug("initialize <-");
		ClassLoader appClassloader = Play.classloader(Play.current());
		ClassLoader libClassloader = getClass().getClassLoader();
		ClassLoader[] classLoaders = new ClassLoader[] { appClassloader, libClassloader };
		ConverterRegistry converters = new ClasspathScanningConverterRegistry(classLoaders);
		models = new ClasspathScanningModelRegistry(converters, classLoaders);
		ControllerRegistry crudControllers = new ClasspathScanningControllerRegistry(global, models, classLoaders);
		dynamicRestController = new RouterRestController(crudControllers, models, ctx);
		dynamicCrudController = new RouterCrudController(crudControllers, models, appClassloader, ctx);
		instance = this;
	}

	@SuppressWarnings("unchecked")
	public <A> A getController(Class<A> type) throws Exception {
		if (RouterRestController.class.equals(type))
			return (A) dynamicRestController;

		if (RouterCrudController.class.equals(type))
			return (A) dynamicCrudController;

		return ctx.getBean(type);
	}

    public static CRUDManager getInstance() {
		return instance;
	}

	public Iterable<String> modelNames() {
        return ImmutableSortedSet.<String>naturalOrder().addAll(models.getModelNames()).build();
    }

}
