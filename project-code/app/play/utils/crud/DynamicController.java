package play.utils.crud;

import java.util.concurrent.Callable;

import org.springframework.util.StringUtils;

import play.Logger;
import play.Logger.ALogger;
import play.cache.Cache;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.utils.meta.CrudControllerRegistry;
import play.utils.meta.IncompatibleControllerException;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@SuppressWarnings("rawtypes")
public abstract class DynamicController extends Controller {
	
	private static ALogger log = Logger.of(DynamicController.class);

	protected ModelRegistry modelRegistry;
	protected CrudControllerRegistry controllerRegistry;

	public DynamicController(CrudControllerRegistry controllerRegistry, ModelRegistry modelRegistry) {
		this.controllerRegistry = controllerRegistry;
		this.modelRegistry = modelRegistry;
	}

	public Result list(String name) {
		if (log.isDebugEnabled())
			log.debug("list <-");
		
		F.Either<ControllerProxyREST, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyREST controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.list();
	}

	public Result create(String name) {
		if (log.isDebugEnabled())
			log.debug("create <- " + name);
		F.Either<ControllerProxyREST, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyREST controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.create();
	}

	public Result show(String name, String key) {
		if (log.isDebugEnabled())
			log.debug("show <- " + name + ", " + key);
		F.Either<ControllerProxyREST, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyREST controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.show(key);
	}

	public Result update(String name, String key) {
		if (log.isDebugEnabled())
			log.debug("update <- " + name + ", " + key);
		F.Either<ControllerProxyREST, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyREST controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.update(key);
	}

    public Result save(String name, String key) {
    	if (StringUtils.hasLength(key))
    		return update(name, key);
    	else
			return create(name);
    }
    
	public Result delete(String name, String key) {
		if (log.isDebugEnabled())
			log.debug("delete <- " + name + ", " + key);
		F.Either<ControllerProxyREST, ? extends Result> cnf = controllerOrNotFound(name);
		if (cnf.right.isDefined())
			return cnf.right.get();
		ControllerProxyREST controller = cnf.left.get();
		if (controller == null) {
			return controllerNotFound(name);
		}
		return controller.delete(key);
	}

	protected Result controllerNotFound(String name) {
		return notFound("Controller not found : " + name);
	}

	protected F.Either<ControllerProxyREST, ? extends Result> controllerOrNotFound(final String name) {
		F.Option<ModelMetadata> modelInfo = getModel(name);
		if (!modelInfo.isDefined())
			return F.Either.Right(notFound("Model with name " + name + " not found!"));

		ModelMetadata model = modelInfo.get();

		F.Option<? extends ControllerProxyREST<?,?>> crud;
		try {
			crud = getController(model);
		} catch (IncompatibleControllerException e) {
			crud = null;
		}

		if (crud == null || !crud.isDefined())
			return F.Either.Right(notFound("Controller for model " + model.getType() + " not found"));

		ControllerProxyREST controller = crud.get();
		return F.Either.Left(controller);
	}

	protected F.Option<ModelMetadata> getModel(final String name) {
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

	protected F.Option<? extends ControllerProxyREST<?,?>> getController(ModelMetadata model)
			throws IncompatibleControllerException {
		Class<?> keyType = model.getKeyField().getType();
		Class<?> modelType = model.getType();
		ControllerProxyREST<?,?> crud = getControllerProxy(keyType, modelType);
		return crud == null ? F.Option.<ControllerProxyREST<?,?>> None() : F.Option.Some(crud);
	}

	protected abstract ControllerProxyREST<?, ?> getControllerProxy(Class<?> keyType, Class<?> modelType) throws IncompatibleControllerException;

}
