package play.utils.meta;

import play.utils.crud.ControllerProxyCRUD;
import play.utils.crud.ControllerProxy;

public interface CrudControllerRegistry {

	<K, M> ControllerProxy<K, M> getRestController(K keyClass, M modelClass) throws IncompatibleControllerException;

	<K, M> ControllerProxyCRUD<K, M> getCrudController(K keyClass, M modelClass) throws IncompatibleControllerException;
}
