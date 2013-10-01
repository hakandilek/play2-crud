package play.utils.meta;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.utils.crud.APIController;
import play.utils.crud.CRUD;
import play.utils.crud.CRUDController;
import play.utils.crud.ControllerProxy;
import play.utils.crud.ControllerProxyCRUD;
import play.utils.crud.ControllerProxyREST;
import play.utils.dyn.Dynamic;

import com.google.common.collect.Maps;

public class ClasspathScanningControllerRegistry implements CrudControllerRegistry {

	private static ALogger log = Logger.of(ClasspathScanningControllerRegistry.class);

	private Map<Class<?>, ControllerProxy<?, ?>> restControllers;
	private Map<Class<?>, ControllerProxyCRUD<?, ?>> crudControllers;

	private ModelRegistry models;

	public ClasspathScanningControllerRegistry(Application app, GlobalSettings global, ModelRegistry models) {
		if (log.isDebugEnabled())
			log.debug("ClasspathScanningControllerRegistry <-");
		if (log.isDebugEnabled())
			log.debug("app : " + app);
		if (log.isDebugEnabled())
			log.debug("global : " + global);

		this.models = models;
		this.restControllers = scanRest(app.classloader(), global, APIController.class);
		this.crudControllers = scanCrud(app.classloader(), global, CRUDController.class);
	}

	@SuppressWarnings("unchecked")
	public <K, M> ControllerProxy<K, M> getRestController(K keyClass, M modelClass, Map<Class<?>, ControllerProxy<?, ?>> controllers) throws IncompatibleControllerException {
		ControllerProxy<?, ?> cp = controllers.get(modelClass);
		
		ControllerProxy<K, M> controller = null;
		try {
			controller = ((ControllerProxy<K, M>) cp);
			if (log.isDebugEnabled())
				log.debug("controller : " + controller);
		} catch (Exception e) {
			throw new IncompatibleControllerException(keyClass, modelClass, cp);
		}

		return controller;
		
	}
	
	@SuppressWarnings("unchecked")
	public <K, M> ControllerProxyCRUD<K, M> getCrudController(K keyClass, M modelClass, Map<Class<?>, ControllerProxyCRUD<?, ?>> controllers) throws IncompatibleControllerException {
		ControllerProxyCRUD<?, ?> cp = controllers.get(modelClass);
		
		ControllerProxyCRUD<K, M> controller = null;
		try {
			controller = ((ControllerProxyCRUD<K, M>) cp);
			if (log.isDebugEnabled())
				log.debug("controller : " + controller);
		} catch (Exception e) {
			throw new IncompatibleControllerException(keyClass, modelClass, cp);
		}

		return controller;
		
	}

	@Override
	public <K, M> ControllerProxy<K, M> getRestController(K keyClass, M modelClass) throws IncompatibleControllerException {
		if (log.isDebugEnabled())
			log.debug("getApiController <- key: " + keyClass + "  model: " + modelClass);
		return getRestController(keyClass, modelClass, restControllers);
	}

	@Override
	public <K, M> ControllerProxyCRUD<K, M> getCrudController(K keyClass, M modelClass) throws IncompatibleControllerException {
		if (log.isDebugEnabled())
			log.debug("getCrudController <- key: " + keyClass + "  model: " + modelClass);
		return getCrudController(keyClass, modelClass, crudControllers);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <C extends CRUD> Map<Class<?>, ControllerProxy<?, ?>> scanRest(ClassLoader classloader, GlobalSettings global, Class<C> superType) {
		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloader)).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner()));

		Map<Class<?>, ControllerProxy<?, ?>> map = Maps.newHashMap();
		Set<Class<? extends C>> controllerClasses = reflections.getSubTypesOf(superType);
		for (Class<? extends C> controllerClass : controllerClasses) {
			try {
				if (log.isDebugEnabled())
					log.debug("controllerClass : " + controllerClass);

				if (controllerClass.isAnnotationPresent(Dynamic.class)) {
					if (log.isDebugEnabled())
						log.debug(controllerClass + "is @" + Dynamic.class);
					continue;
				}

				C controller = global.getControllerInstance(controllerClass);
				if (controller != null) {
					Class modelClass = controller.getModelClass();
					log.info("Found controller:" + controllerClass + " (" + modelClass + ")");
					ModelMetadata model = models.getModel(modelClass);
					map.put(modelClass, new ControllerProxyREST(controller, model));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <C extends CRUD> Map<Class<?>, ControllerProxyCRUD<?, ?>> scanCrud(ClassLoader classloader, GlobalSettings global, Class<C> superType) {
		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloader)).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner()));

		Map<Class<?>, ControllerProxyCRUD<?, ?>> map = Maps.newHashMap();
		Set<Class<? extends C>> controllerClasses = reflections.getSubTypesOf(superType);
		for (Class<? extends C> controllerClass : controllerClasses) {
			try {
				if (log.isDebugEnabled())
					log.debug("controllerClass : " + controllerClass);
				
				if (controllerClass.isAnnotationPresent(Dynamic.class)) {
					if (log.isDebugEnabled())
						log.debug(controllerClass + "is @" + Dynamic.class);
					continue;
				}

				C controller = global.getControllerInstance(controllerClass);
				if (controller != null) {
					Class modelClass = controller.getModelClass();
					log.info("Found controller:" + controllerClass + " (" + modelClass + ")");
					ModelMetadata model = models.getModel(modelClass);
					map.put(modelClass, new ControllerProxyCRUD(controller, model));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
