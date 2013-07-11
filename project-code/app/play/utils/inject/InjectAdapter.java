package play.utils.inject;

import play.Logger;
import play.Logger.ALogger;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectAdapter {

	private static ALogger log = Logger.of(InjectAdapter.class);

	private static InjectAdapter instance;

	private Injector injector;

	private InjectAdapter() {
		super();
		injector = Guice.createInjector();
		instance = this;
	}

	public static InjectAdapter getInstance() {
		if (instance == null) {
			instance = new InjectAdapter();
		}
		return instance;
	}

	public <T> T getInstance(Class<T> paramClass) {
		if (injector == null)
			log.warn("injector is null");

		T object = injector.getInstance(paramClass);
		if (log.isDebugEnabled())
			log.debug("class : " + paramClass);
		if (log.isDebugEnabled())
			log.debug("instance : " + object);
		
		return object;
	}

}
