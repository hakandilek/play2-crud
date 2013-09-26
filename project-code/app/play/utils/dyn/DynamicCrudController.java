package play.utils.dyn;

import static play.data.Form.*;

import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.meta.ModelMetadata;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicCrudController extends CRUDController {
	
	private ModelMetadata model;
	private String templateList;
	private String templateForm;
	private String templateShow;
	private Call indexCall;

	public DynamicCrudController(ClassLoader classLoader, ModelMetadata model, Call indexCall) {
		super(classLoader, new DynamicDAO(model), form(model.getType()), model.getKeyField().getType(), model.getType(), 10, model.getKeyField().getField().getName());
		this.indexCall = indexCall;
		templateList = model.getName() + "List";
		templateForm = model.getName() + "Form";
		templateShow = model.getName() + "Show";
	}

	@Override
	protected String templateForList() {
		return templateList;
	}

	@Override
	protected String templateForForm() {
		return templateForm;
	}

	@Override
	protected String templateForShow() {
		return templateShow;
	}

	@Override
	protected Call toIndex() {
		return indexCall;
	}

	@Override
	public Result read(Object key) {
		return show(key);
	}

	@Override
	public Result list() {
		return list(0);
	}

}
