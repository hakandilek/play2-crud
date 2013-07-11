package play.utils.crud;

import java.util.concurrent.Callable;

import play.cache.Cache;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.utils.meta.ControllerProxy;
import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class DynamicRestController extends Controller {

	private ModelRegistry modelRegistry;
	private CrudControllerRegistry controllerRegistry;

	public DynamicRestController(CrudControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		this.controllerRegistry = controllerRegistry;
		this.modelRegistry = modelRegistry;
	}

	public Result list(final String name) {
		F.Either<F.Tuple<ModelMetadata, ControllerProxy>, ? extends Result> controller = controllerOrNotFound(name);
		if (controller.right.isDefined())
			return controller.right.get();
		F.Tuple<ModelMetadata, ControllerProxy> tuple = controller.left.get();
		ModelMetadata model = tuple._1;
		ControllerProxy cont = tuple._2;
		return cont.list(model);
	}

	public Result create(String name) {
		// TODO:not implemented
		return TODO;
	}

	public Result show(String name, String key) {
		// TODO:not implemented
		return TODO;
	}

	public Result update(String name, String key) {
		// TODO:not implemented
		return TODO;
	}

	public Result delete(String name, String key) {
		// TODO:not implemented
		return TODO;
	}

	private F.Either<F.Tuple<ModelMetadata, ControllerProxy>, ? extends Result> controllerOrNotFound(final String name) {
		F.Option<ModelMetadata> modelInfo = getModel(name);
		if (!modelInfo.isDefined())
			return F.Either.Right(notFound("Model with name " + name + " not found!"));

		ModelMetadata model = modelInfo.get();

		F.Option<? extends ControllerProxy> crud = getController(model);
		if (!crud.isDefined())
			return F.Either.Right(notFound("Controller for model " + model.getType() + " not found"));

		ControllerProxy controller = crud.get();

		return F.Either.Left(new F.Tuple<ModelMetadata, ControllerProxy>(model, controller));
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

	private F.Option<? extends ControllerProxy> getController(ModelMetadata model) {
		ControllerProxy crud = controllerRegistry.getController(model.getKeyField().getType(), model.getType());
		return crud == null ? F.Option.<ControllerProxy> None() : F.Option.Some(crud);

	}

}
