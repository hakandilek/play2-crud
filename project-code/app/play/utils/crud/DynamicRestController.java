package play.utils.crud;

import java.util.concurrent.Callable;

import play.cache.Cache;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.utils.meta.ControllerProxy;
import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@SuppressWarnings("rawtypes")
public class DynamicRestController extends Controller {

	private ModelRegistry modelRegistry;
	private CrudControllerRegistry controllerRegistry;

	public DynamicRestController(CrudControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		this.controllerRegistry = controllerRegistry;
		this.modelRegistry = modelRegistry;
	}

	public Result list(String name) {
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxy controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.list();
	}

	public Result create(String name) {
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxy controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.create();
	}

	public Result show(String name, String key) {
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxy controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.show(key);
	}

	public Result update(String name, String key) {
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxy controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.update(key);
	}

	public Result delete(String name, String key) {
		F.Either<ControllerProxy, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxy controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.delete(key);
	}

	private Result controllerNotFound(String name) {
		return notFound("Controller not found : " + name);
	}

	private F.Either<ControllerProxy, ? extends Result> controllerOrNotFound(final String name) {
		F.Option<ModelMetadata> modelInfo = getModel(name);
		if (!modelInfo.isDefined())
			return F.Either.Right(notFound("Model with name " + name + " not found!"));

		ModelMetadata model = modelInfo.get();

		F.Option<? extends ControllerProxy<?,?>> crud;
		try {
			crud = getController(model);
		} catch (IncompatibleControllerException e) {
			crud = null;
		}

		if (crud == null || !crud.isDefined())
			return F.Either.Right(notFound("Controller for model " + model.getType() + " not found"));

		ControllerProxy controller = crud.get();
		return F.Either.Left(controller);
	}

	private F.Option<ModelMetadata> getModel(final String name) {
		ModelMetadata modelInfo = null;
		try {
			modelInfo = (ModelMetadata) Cache.getOrElse(getClass().getName() + "_ModelMetadata_" + name,
					new Callable<Object>() {

						@Override
						public Object call() throws Exception {
							return Iterables.find(modelRegistry.getModels(), new Predicate<ModelMetadata>() {
								@Override
								public boolean apply(ModelMetadata model) {
									String modelName = model.getName();
									return modelName.equals(name);
								}
							}, null);
						}
					}, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelInfo == null ? F.Option.<ModelMetadata> None() : F.Option.Some(modelInfo);
	}

	private F.Option<? extends ControllerProxy<?,?>> getController(ModelMetadata model)
			throws IncompatibleControllerException {
		ControllerProxy<?,?> crud = controllerRegistry.getController(model.getKeyField().getType(), model.getType());
		return crud == null ? F.Option.<ControllerProxy<?,?>> None() : F.Option.Some(crud);

	}

}
