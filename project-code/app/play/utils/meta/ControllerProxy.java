package play.utils.meta;

import play.mvc.Controller;
import play.mvc.Result;
import play.utils.crud.APIController;

public class ControllerProxy extends Controller {

	private APIController<?, ?> delegate;

	public ControllerProxy(APIController<?,?> delegate) {
		this.delegate = delegate;
	}

	public Result list(ModelMetadata model) {
		return delegate.list();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ControllerProxy [").append(delegate).append("]");
		return builder.toString();
	}
	
}
