package play.utils.meta;

public interface CrudControllerRegistry {

	<K, M> ControllerProxy getController(K keyClass, M modelClass);

}
