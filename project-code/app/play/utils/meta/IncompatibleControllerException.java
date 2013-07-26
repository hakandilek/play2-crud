package play.utils.meta;

public class IncompatibleControllerException extends Exception {

	private static final long serialVersionUID = 1L;

	public <K, M> IncompatibleControllerException(K keyClass, M modelClass, ControllerProxy<?, ?> cp) {
		super("Controller (" + cp + ") is incompatible with key:" + keyClass + " and model:" + modelClass);
	}

}
