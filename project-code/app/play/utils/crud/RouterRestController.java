package play.utils.crud;

import play.utils.dyn.DynamicRestController;
import play.utils.meta.ControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

public class RouterRestController extends RouterController {

	public RouterRestController(ControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		super(controllerRegistry, modelRegistry);
	}

	@Override
	protected ControllerProxy<?, ?> getControllerProxy(Class<?> keyType, Class<?> modelType)
			throws IncompatibleControllerException {
		return controllerRegistry.getRestController(keyType, modelType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ControllerProxy<?, ?> getDynamicController(Class<?> keyType, Class<?> modelType, ModelMetadata model) {
		ControllerProxy<?, ?> proxy = dynamicRestControllers.get(modelType);
		if (proxy == null) {
			DynamicRestController dynController = new DynamicRestController(model);
			proxy = new ControllerProxyREST(dynController, model);
			dynamicRestControllers.put(modelType, proxy);
		}
		return proxy;
	}

}
