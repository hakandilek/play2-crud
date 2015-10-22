package play.utils.crud;

import play.mvc.Result;
import play.utils.meta.ModelMetadata;

import java.io.Serializable;

public class ControllerProxyCRUD<M, K extends Serializable> extends ControllerProxy<M, K> {

	public ControllerProxyCRUD(CRUD<M, K> delegate, ModelMetadata model) {
		super(delegate, model);
	}

	@SuppressWarnings("rawtypes")
	public Result newForm() {
		return ((CRUDController)delegate).newForm();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result editForm(String key) {
		K k = keyConverter.convert(key);
		return ((CRUDController)delegate).editForm(k);
	}

}
