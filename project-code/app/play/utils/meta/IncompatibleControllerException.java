package play.utils.meta;

import play.utils.crud.ControllerProxyREST;

public class IncompatibleControllerException extends Exception {

	private static final long serialVersionUID = 1L;

	public <K, M> IncompatibleControllerException(K keyClass, M modelClass, ControllerProxyREST<?, ?> cp) {
		super("Controller (" + cp + ") is incompatible with key:" + keyClass + " and model:" + modelClass);
	}

}
