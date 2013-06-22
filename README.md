play2-crud
==========

simple CRUD &amp; DAO implementation for [play framework](http://github.com/playframework/play) 2.0 and 2.1

# Sample
   
   [Here](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-crud-sample) is a full featured sample.
   
# HOW-TO

Follow these steps to use play2-crud. You can also use it partially just for DAO or CRUD controllers. If you think any part needs further explanation, please report a new issue.

## Define model

 * Model class have to implement `play.utils.dao.BasicModel` with the type parameter indicating the type of the `@Id` field.

```java
@Entity
public class Sample extends Model implements BasicModel<Long> {

   @Id
   private Long key;

   @Basic
   @Required
   private String name;

   public Long getKey() {
      return key;
   }

   public void setKey(Long key) {
      this.key = key;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
```

 * Here the `Sample` model class implements `BasicModel<Long>` where `key` field indicated with `@Id` is `Long`.

## Define DAO

 * DAO class extends `play.utils.dao.BasicDAO` with two type parameters: One for the key type and one for the Model type
 * Alternatively, `play.utils.dao.CachedDAO` cn be used to cache fetched data.
 * Classes implementing both `BasicDAO` or `CachedDAO` should override the constructor with key ad model class type parameters.

```java
public class SampleDAO extends BasicDAO<Long, Sample> {
   public SampleDAO() {
      super(Long.class, Sample.class);
   }
}
```
 * Here the `SampleDAO` extends `BasicDAO<Long, Sample>` with `Long` key type, and `Sample` model type
 * and overrides the super class constructor with `super(Long.class, Sample.class)`
 
## Define Controller

 * Controller classes extend `play.utils.crud.CRUDController` with key and model type parameters as in DAO classes.
 * Controllers should override the constructor by providing
   * DAO implementation
   * A Form class
   * Key type
   * Model type
   * Page size (used for paging lists)
   * Sorting field as string (might be in the form of `"fieldName [asc]/[desc]"`)
 * Template file name prefixes for List/Form and Show pages
 * A Call to the index page, indicating the call to return after CRUD operations
 
```java
import static play.data.Form.*;
import play.mvc.Call;

public class SampleController extends CRUDController<Long, Sample> {
   
   public SampleController(SampleDAO dao) {
      super(dao, form(Sample.class), Long.class, Sample.class, 10, "name");
   }

   protected String templateForList() {
      return "sampleList";
   }

   protected String templateForForm() {
      return "sampleForm";
   }

   protected String templateForShow() {
      return "sampleShow";
   }

   @Override
   protected Call toIndex() {
      return routes.Application.sampleList();
   }
}
```

      
## Associate Controller

```java
public class Application extends Controller {

   private static SampleDAO sampleDAO = new SampleDAO();
   private static SampleController sampleController = new SampleController(sampleDAO);

   public static Result index() {
      return ok(views.html.index.render());
   }

   public static Result sampleList() {
      return sampleController.listAll();
   }

   public static Result sampleNewForm() {
      return sampleController.newForm();
   }

   public static Result sampleCreate() {
      return sampleController.create();
   }

   public static Result sampleEditForm(Long key) {
      return sampleController.editForm(key);
   }

   public static Result sampleUpdate(Long key) {
      return sampleController.update(key);
   }

   public static Result sampleDelete(Long key) {
      return sampleController.delete(key);
   }

   public static Result sampleShow(Long key) {
      return sampleController.show(key);
   }
}
```

## define routes

```
# Home page
GET     /                           controllers.Application.index()
# productScrap CRUD
GET     /sample                    controllers.Application.sampleList()
POST    /sample                    controllers.Application.sampleCreate()
GET     /sample/new                controllers.Application.sampleNewForm()
GET     /sample/:key/edit          controllers.Application.sampleEditForm(key: Long)
POST    /sample/:key/update        controllers.Application.sampleUpdate(key: Long)
GET     /sample/:key/delete        controllers.Application.sampleDelete(key: Long)
GET     /sample/:key               controllers.Application.sampleShow(key: Long)
```

## define templates

 * [form](https://github.com/hakandilek/play2-crud/blob/master/samples/play2-crud-sample/app/views/sampleForm.scala.html)
 * [list](https://github.com/hakandilek/play2-crud/blob/master/samples/play2-crud-sample/app/views/sampleList.scala.html)
 * and [show](https://github.com/hakandilek/play2-crud/blob/master/samples/play2-crud-sample/app/views/sampleShow.scala.html) templates 
 
... and voila!


# HOW-TO use DAO Listeners

This part introduces how to use DAO listeners

## Additions to the model 

 * Define an additional attribute in your Model, which will be updated by the listener. 
   * Let's define a new `int randomValue` field, that will be randomly updated on creation and when the model is updated.
   
```java
public class Sample extends Model implements BasicModel<Long> {

...

   @Basic
	private int randomValue;

...

   public int getRandomValue() {
		return randomValue;
	}

	public void setRandomValue(int randomValue) {
		this.randomValue = randomValue;
	}
   
...
```

## Create a new `DAOListener`

 * Create a `DAOListener` that updates the `randomValue` field before create and update operations:
 
```java
public class SampleDAOListener implements DAOListener<Long, Sample> {

   Random random = new Random();
	

	@Override
	public void beforeCreate(Sample m) {
		m.setRandomValue(random.nextInt());
	}

	@Override
	public void beforeUpdate(Sample m) {
		m.setRandomValue(random.nextInt());
	}

	@Override
	public void afterCreate(Long key, Sample m) {
	}

	@Override
	public void afterRemove(Long key, Sample m) {
	}

	@Override
	public void afterUpdate(Sample m) {
	}

	@Override
	public void beforeRemove(Long key) {
	}
}
```

## Register the `DAOListener` to the `DAO`

 * Add the `SampleDAOListener` in `SampleDAO` constructor: 
 
 
```java
   public SampleDAO() {
		super(Long.class, Sample.class);
		addListener(new SampleDAOListener());
	}
```

## Make changes in your templates

 * Make some changes (e.g. list template) if you want to display the new field in the templates.
 
