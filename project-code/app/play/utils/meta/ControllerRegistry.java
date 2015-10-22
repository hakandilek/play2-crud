package play.utils.meta;

import play.utils.crud.ControllerProxyCRUD;
import play.utils.crud.ControllerProxy;

import java.io.Serializable;

public interface ControllerRegistry {

	<M, K extends Serializable> ControllerProxy<M, K> getRestController(K keyClass, M modelClass) throws IncompatibleControllerException;

	<M, K extends Serializable> ControllerProxyCRUD<M, K> getCrudController(K keyClass, M modelClass) throws IncompatibleControllerException;
}
