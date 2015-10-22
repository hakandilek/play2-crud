package play.utils.crud;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import play.Logger;
import play.Logger.ALogger;
import play.api.mvc.Call;
import play.libs.F;
import play.mvc.Result;
import play.utils.dyn.DynamicCrudController;
import play.utils.meta.ControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

@SuppressWarnings("rawtypes")
public class RouterCrudController extends RouterController {

	private static ALogger log = Logger.of(RouterCrudController.class);
	private ClassLoader classLoader;
	private ConfigurableApplicationContext ctx;
	
	public RouterCrudController(ControllerRegistry controllerRegistry, ModelRegistry modelRegistry, ClassLoader classLoader, ConfigurableApplicationContext ctx) {
		super(controllerRegistry, modelRegistry);
		this.classLoader = classLoader;
		this.ctx = ctx;
	}

	public Result newForm(String name) {
		if (log.isDebugEnabled())
			log.debug("newForm <- " + name);
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined()) {
			return cnf.right.get();
		}
		ControllerProxyCRUD controller = (ControllerProxyCRUD) cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.newForm();
	}
	
	public Result editForm(String name, String key) {
		if (log.isDebugEnabled())
			log.debug("editForm <- " + name + ", " + key);
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined()) {
			return cnf.right.get();
		}
		ControllerProxyCRUD controller = (ControllerProxyCRUD) cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.editForm(key);
	}
	
	@SuppressWarnings("unchecked")
	protected ControllerProxy<?, ?> getDynamicController(Class<?> keyType, Class<?> modelType, ModelMetadata model) {
		ControllerProxy<?, ?> proxy = dynamicCrudControllers.get(modelType);
		if (proxy == null) {
			Call indexCall = routes.RouterCrudController.list(model.getName());
			String repositoryBeanName = Character.toLowerCase(model.getName().charAt(0)) + model.getName().substring(1)+"Repository";
			DynamicCrudController dynController = new DynamicCrudController(classLoader, model, indexCall, ctx.getBean(repositoryBeanName, JpaRepository.class));
			proxy = new ControllerProxyCRUD<>(dynController, model);
			dynamicCrudControllers.put(modelType, proxy);
		}
		return proxy;
	}

	protected ControllerProxy<?, ?> getControllerProxy(Class<?> keyType, Class<?> modelType) throws IncompatibleControllerException {
		return controllerRegistry.getCrudController(keyType, modelType);
	}

	public Result index() {
		return ok(play.utils.crud.views.html.index.render());
	}

}
