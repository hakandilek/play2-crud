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

import com.google.common.collect.Maps;

public class ClasspathScanningControllerRegistry implements CrudControllerRegistry {

	private static ALogger log = Logger.of(ClasspathScanningControllerRegistry.class);

	private Map<Class<?>, ControllerProxy> controllers;


	public ClasspathScanningControllerRegistry(Application app, GlobalSettings global) {
		if (log.isDebugEnabled())
			log.debug("ClasspathScanningControllerRegistry <-");
		if (log.isDebugEnabled())
			log.debug("app : " + app);
		if (log.isDebugEnabled())
			log.debug("global : " + global);
		
		controllers = scan(app.classloader(), global);
	}

	@Override
	public <K, M> ControllerProxy getController(K keyClass, M modelClass) {
		if (log.isDebugEnabled())
			log.debug("getController <- key: " + keyClass + "  model: " + modelClass);

		ControllerProxy controller = controllers.get(modelClass);
		if (log.isDebugEnabled())
			log.debug("controller : " + controller);

		return controller;
	}

	@SuppressWarnings("rawtypes")
	private Map<Class<?>, ControllerProxy> scan(ClassLoader classloader, GlobalSettings global) {
		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloader)).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner()));

		Map<Class<?>, ControllerProxy> map = Maps.newHashMap();
		Set<Class<? extends APIController>> controllerClasses = reflections.getSubTypesOf(APIController.class);
		for (Class<? extends APIController> controllerClass : controllerClasses) {
			try {
				if (log.isDebugEnabled())
					log.debug("controllerClass : " + controllerClass);

				APIController controller = global.getControllerInstance(controllerClass);
				if (controller != null) {
					Class modelClass = controller.getModelClass();
					log.info("Found controller:" + controllerClass + " (" + modelClass + ")");
					map.put(modelClass, new ControllerProxy(controller));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
