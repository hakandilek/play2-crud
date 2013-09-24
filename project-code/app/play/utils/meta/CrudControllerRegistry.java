package play.utils.meta;

import play.utils.crud.ControllerProxyCRUD;
import play.utils.crud.ControllerProxyREST;

public interface CrudControllerRegistry {

	<K, M> ControllerProxyREST<K, M> getRestController(K keyClass, M modelClass) throws IncompatibleControllerException;

	<K, M> ControllerProxyCRUD<K, M> getCrudController(K keyClass, M modelClass) throws IncompatibleControllerException;
}
