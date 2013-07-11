package play.utils.crud;

import play.Application;
import play.GlobalSettings;

public class GlobalCrudSettings extends GlobalSettings {

	private CrudManager manager;

	@Override
	public <A> A getControllerInstance(Class<A> type) throws Exception {
		A crudController = manager.getController(type);
		if (crudController != null)
			return crudController;
		return super.getControllerInstance(type);
	}

	@Override
	public void onStart(Application app) {
		super.onStart(app);
		
		manager = new CrudManager(this);
		manager.initialize(app);
	}

}
