package play.utils.meta;

public interface CrudControllerRegistry {

	<K, M> ControllerProxy<K, M> getController(K keyClass, M modelClass) throws IncompatibleControllerException;

}
