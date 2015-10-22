package play.utils.crud;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import play.utils.dyn.DynamicRestController;
import play.utils.meta.ControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

public class RouterRestController extends RouterController {

	private ConfigurableApplicationContext ctx;

	public RouterRestController(ControllerRegistry controllerRegistry, ModelRegistry modelRegistry, ConfigurableApplicationContext ctx) {
		super(controllerRegistry, modelRegistry);
		this.ctx = ctx;
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
			String repositoryBeanName = Character.toLowerCase(model.getName().charAt(0)) + model.getName().substring(1)+"Repository";
			DynamicRestController dynController = new DynamicRestController(model, ctx.getBean(repositoryBeanName, JpaRepository.class));
			proxy = new ControllerProxyREST(dynController, model);
			dynamicRestControllers.put(modelType, proxy);
		}
		return proxy;
	}

}
