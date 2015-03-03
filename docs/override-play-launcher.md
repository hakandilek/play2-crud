## Override play launcher

58	If you want to override the play application launcher, you must create your own class which extends GlobalCRUDSettings. This class must be in the folder projectRoot/app/
59	```java
60	import play.Logger;
61	import play.Application;
62	import play.utils.crud.GlobalCRUDSettings;
63	
64	public class Global extends GlobalCRUDSettings
65	{
66	    @Override
67	    public void onStart(Application app) {
68	        super.onStart(app);
69	        Logger.info("Application started !");
70	    }
71	}
72	
73	```
74	Now notice play that the application launcher is now the class you created. Change the `application.global` configuration key in the `conf/application.conf` file, and use the class you created :
75	```
76	...
77	application.global=Global
78	...
