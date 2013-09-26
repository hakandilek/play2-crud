package play.utils.crud;

import play.utils.meta.ModelMetadata;

public class ControllerProxyREST<K, M> extends ControllerProxy<K, M> {

	public ControllerProxyREST(CRUD<K, M> delegate, ModelMetadata model) {
		super(delegate, model);
	}

}
