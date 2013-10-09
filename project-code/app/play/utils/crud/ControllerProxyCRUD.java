package play.utils.crud;

import play.mvc.Result;
import play.utils.meta.ModelMetadata;

public class ControllerProxyCRUD<K, M> extends ControllerProxy<K, M> {

	public ControllerProxyCRUD(CRUD<K, M> delegate, ModelMetadata model) {
		super(delegate, model);
	}

	@SuppressWarnings("rawtypes")
	public Result newForm() {
		return ((CRUDController)delegate).newForm();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result editForm(String key) {
		return ((CRUDController)delegate).editForm(key);
	}

}
