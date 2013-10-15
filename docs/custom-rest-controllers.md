## HOW-TO define custom REST Controllers

This part introduces how to use DAO listeners

### Define a REST controller

 * Define a REST controller extending `APIController` 
   * The REST controller has to implement the create() method for creating the entity. 
   * There are several helper methods like `checkRequired` and `jsonText` that can be used in the REST controller.
   
```java
import static play.libs.Json.toJson;

import com.google.common.collect.ImmutableMap;

import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Result;
import play.utils.crud.APIController;

public class SampleRestController extends APIController<Long, Sample> {

	public SampleRestController(SampleDAO dao) {
		super(dao);
	}

	@Override
	public Result create() {
		Result check = checkRequired("name");
		if (check != null) {
			if (log.isDebugEnabled())
				log.debug("check : " + check);
			return check;
		}

		String name = jsonText("name");

		Sample m = new Sample();
		m.setName(name);
		
		Long key = dao.create(m);
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		return created(toJson(ImmutableMap.of("status", "OK", "key", key)));
	}

}
``` 

### Associate REST Controller
 
 * Associate in the existing Application Controller 

```java
public class Application extends Controller {

...

	private static SampleRestController restController = new SampleRestController(sampleDAO);
	
...

	public static Result restList() {
		return restController.list();
	}

	public static Result restCreate() {
		return restController.create();
	}

	public static Result restUpdate(Long key) {
		return restController.update(key);
	}

	public static Result restDelete(Long key) {
		return restController.delete(key);
	}

	public static Result restGet(Long key) {
		return restController.get(key);
	}
}

```


### define routes

```
...

# REST API
GET     /api/sample                controllers.Application.restList()
GET     /api/sample/:key           controllers.Application.restGet(key:Long)
PUT     /api/sample/:key           controllers.Application.restUpdate(key:Long)
POST    /api/report/        	   controllers.Application.restCreate()
DELETE  /api/sample/:key           controllers.Application.restDelete(key:Long)
```
