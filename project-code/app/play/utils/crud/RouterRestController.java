package play.utils.crud;

import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelRegistry;

public class RouterRestController extends RouterController {

	public RouterRestController(CrudControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		super(controllerRegistry, modelRegistry);
	}

	@Override
	protected ControllerProxyREST<?, ?> getControllerProxy(Class<?> keyType, Class<?> modelType)
			throws IncompatibleControllerException {
		return controllerRegistry.getRestController(keyType, modelType);
	}
	
}
