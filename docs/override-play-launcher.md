## Override play launcher

If you want to override the play application launcher, you must create your own class which extends `GlobalCRUDSettings`. This class must be in the folder `projectRoot/app/`

```java
import play.Logger;
import play.Application;
import play.utils.crud.GlobalCRUDSettings;

public class Global extends GlobalCRUDSettings
{
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        Logger.info("Application started !");
    }
}

```
Now notice play that the application launcher is now the class you created. Change the `application.global` configuration key in the `conf/application.conf` file, and use the class you created :
```
...
application.global=Global
...
```
