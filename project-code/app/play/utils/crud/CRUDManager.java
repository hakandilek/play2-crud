package play.utils.crud;

import com.google.common.collect.ImmutableSortedSet;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.api.Play;
import play.utils.inject.InjectAdapter;
import play.utils.meta.ControllerRegistry;
import play.utils.meta.ConverterRegistry;
import play.utils.meta.ModelRegistry;
import play.utils.meta.cp.ClasspathScanningControllerRegistry;
import play.utils.meta.cp.ClasspathScanningConverterRegistry;
import play.utils.meta.cp.ClasspathScanningModelRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CRUDManager {

	private static ALogger log = Logger.of(CRUDManager.class);

	GlobalSettings global;

	InjectAdapter injections = InjectAdapter.getInstance();

	RouterRestController dynamicRestController;

	RouterCrudController dynamicCrudController;

    Class<? extends RouterCrudController> crudControllerClass;

    ClasspathScanningModelRegistry models;

	private static CRUDManager instance;

	public CRUDManager(GlobalSettings global) {
		this.global = global;
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
		dynamicRestController = new RouterRestController(crudControllers, models);

        crudControllerClass = initCrudControllerClass(appClassloader);
		dynamicCrudController = initCrudController(appClassloader, crudControllers);

		instance = this;
	}

    private RouterCrudController initCrudController(ClassLoader appClassloader, ControllerRegistry crudControllers) {
        try {
            Constructor<?> constructor = crudControllerClass.getConstructor(ControllerRegistry.class, ModelRegistry.class, ClassLoader.class);
            return (RouterCrudController) constructor.newInstance(crudControllers, models, appClassloader);

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<? extends RouterCrudController> initCrudControllerClass(ClassLoader appClassloader) {
        String crudControllerConf = play.Play.application().configuration().getString("crud.controller.class");
        if (crudControllerConf != null && !"".equals(crudControllerConf)) {
            try {
                return (Class<? extends RouterCrudController>) Class.forName(crudControllerConf, true, appClassloader);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return RouterCrudController.class;
        }

    }

    @SuppressWarnings("unchecked")
	public <A> A getController(Class<A> type) throws Exception {
		if (RouterRestController.class.equals(type))
			return (A) dynamicRestController;

		if (crudControllerClass.equals(type))
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
