package play.utils.meta;

import play.mvc.Controller;
import play.mvc.Result;
import play.utils.crud.APIController;

public class ControllerProxy<K, M> extends Controller {

	private APIController<K, M> delegate;
	private ModelMetadata model;
	private KeyConverter keyConverter;

	public ControllerProxy(APIController<K, M> delegate, ModelMetadata model) {
		this.delegate = delegate;
		this.model = model;
		this.keyConverter = model.getKeyConverter();
	}

	public Result list() {
		return delegate.list();
	}

	public Result create() {
		return delegate.create();
	}

	public Result show(String key) {
		K k = keyConverter.convert(key);
		return delegate.get(k);
	}

	public Result update(String key) {
		K k = keyConverter.convert(key);
		return delegate.update(k);
	}

	public Result delete(String key) {
		K k = keyConverter.convert(key);
		return delegate.delete(k);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ControllerProxy(" + model.getName() + ") [").append(delegate).append("]");
		return builder.toString();
	}
}
