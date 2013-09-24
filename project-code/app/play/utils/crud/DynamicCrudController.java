package play.utils.crud;

import play.Logger;
import play.Logger.ALogger;
import play.libs.F;
import play.mvc.Result;
import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

@SuppressWarnings("rawtypes")
public class DynamicCrudController extends DynamicController {

	private static ALogger log = Logger.of(DynamicCrudController.class);
	
	public DynamicCrudController(CrudControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		super(controllerRegistry, modelRegistry);
	}

	public Result newForm(String name) {
		if (log.isDebugEnabled())
			log.debug("newForm <- " + name);
		F.Either<ControllerProxyCRUD, ? extends Result> cnf = crudControllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyCRUD controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.newForm();
	}
	
	protected F.Either<ControllerProxyCRUD, ? extends Result> crudControllerOrNotFound(final String name) {
		F.Option<ModelMetadata> modelInfo = getModel(name);
		if (!modelInfo.isDefined())
			return F.Either.Right(notFound("Model with name " + name + " not found!"));

		ModelMetadata model = modelInfo.get();

		F.Option<? extends ControllerProxyCRUD<?,?>> crud;
		try {
			crud = getCrudController(model);
		} catch (IncompatibleControllerException e) {
			crud = null;
		}

		if (crud == null || !crud.isDefined())
			return F.Either.Right(notFound("Controller for model " + model.getType() + " not found"));

		ControllerProxyCRUD controller = crud.get();
		return F.Either.Left(controller);
	}
	
	protected F.Option<? extends ControllerProxyCRUD<?,?>> getCrudController(ModelMetadata model)
			throws IncompatibleControllerException {
		Class<?> keyType = model.getKeyField().getType();
		Class<?> modelType = model.getType();
		ControllerProxyCRUD<?,?> crud = getControllerProxy(keyType, modelType);
		return crud == null ? F.Option.<ControllerProxyCRUD<?,?>> None() : F.Option.Some(crud);
	}
	
	protected ControllerProxyCRUD<?, ?> getControllerProxy(Class<?> keyType, Class<?> modelType) throws IncompatibleControllerException {
		return controllerRegistry.getCrudController(keyType, modelType);
	}


}
