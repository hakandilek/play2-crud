package play.utils.crud;

import play.utils.meta.ModelMetadata;

import java.io.Serializable;

public class ControllerProxyREST<M, K extends Serializable> extends ControllerProxy<M, K> {

	public ControllerProxyREST(CRUD<M, K> delegate, ModelMetadata model) {
		super(delegate, model);
	}

}
