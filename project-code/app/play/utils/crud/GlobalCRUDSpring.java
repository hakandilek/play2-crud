package play.utils.crud;

import com.teksourcery.configuration.AppContext;
import com.teksourcery.toolbox.util.Output;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import play.Application;
import play.GlobalSettings;
import play.utils.crud.CRUDManager;

/**
 * Application wide behaviour. We establish a Spring application context for the dependency injection system and
 * configure Spring Data.
 */
public class GlobalCRUDSpring extends GlobalSettings {

    private CRUDManager manager;

	/**
	 * The name of the persistence unit we will be using.
	 */
	static final String DEFAULT_PERSISTENCE_UNIT = "default";

	/**
	 * Declare the application context to be used - a Java annotation based application context requiring no XML.
	 */
	final private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

	/**
	 * Sync the context lifecycle with Play's.
	 */
	@Override
	public void onStart(final Application app) {
		super.onStart(app);

		// AnnotationConfigApplicationContext can only be refreshed once, but we do it here even though this method
		// can be called multiple times. The reason for doing during startup is so that the Play configuration is
		// entirely available to this application context.
		ctx.register(SpringDataJpaConfiguration.class);
		ctx.scan("controllers", "models", "play.utils");
		ctx.refresh();

		// This will construct the beans and call any construction lifecycle methods e.g. @PostConstruct
		ctx.start();

		manager = new CRUDManager(this, ctx);
		manager.initialize(app);

		System.out.println("-- Models found by CRUDManager --");
		for(String model : manager.modelNames()) {
			System.out.println(model);
		}

		System.out.println("-- Beans found by CTX --");
		Output.print(ctx.getBeansOfType(Object.class));
	}

	/**
	 * Sync the context lifecycle with Play's.
	 */
	@Override
	public void onStop(final Application app) {
		// This will call any destruction lifecycle methods and then release the beans e.g. @PreDestroy
		ctx.close();

		super.onStop(app);
	}

	/**
	 * Controllers must be resolved through the application context. There is a special method of GlobalSettings that we
	 * can override to resolve a given controller. This resolution is required by the Play router.
	 */
	@Override
	public <A> A getControllerInstance(Class<A> aClass) {
        A crudController = null;
        try {
            crudController = manager.getController(aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (crudController != null)
            return crudController;
        return ctx.getBean(aClass);
	}

	/**
	 * This configuration establishes the Spring context which in our case is defined in the Document Engine project.
	 */
	@Configuration
	@Import(AppContext.class)
	public static class SpringDataJpaConfiguration {
		// At the moment this class is just a entry point for the Document Engine's Spring context config, AppContext
	}

}
