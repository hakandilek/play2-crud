package play.utils.crud;

import play.mvc.Result;
import play.utils.meta.ModelMetadata;

public class ControllerProxyCRUD<K, M> extends ControllerProxyREST<K, M> {

	public ControllerProxyCRUD(CRUD<K, M> delegate, ModelMetadata model) {
		super(delegate, model);
	}

	@SuppressWarnings("rawtypes")
	public Result newForm() {
		return ((CRUDController)delegate).newForm();
	}

}
