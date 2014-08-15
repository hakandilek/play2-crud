
## HOW-TO Define Custom Controller
### General

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
 * An `@Inject` (javax.inject.Inject) annotation may be required before the construction definition.
 
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
[Definition of the class SampleDAO is here.](custom-dao.md)

###Overriding controller actions
Since the actions of the CrudController are not static, you can override them and use generic types !

List of the functions you can override :
```java
//Send an empty new form
public Result newForm();
//Process the creation of the new object and redirect to the index
public Result create();
//Send an update form for the object of id key
public Result editForm(K key);
//Process the update and redirect to the index
public Result update(K key);
//Send a show page
public Result show(K key);
//Process the deletion and redirect to the index
public Result delete(K key);
//Send a list page
public Result list();
```

Example: 
If you want to make sure someone is logged in before sending a new form, here is how to do it :
```java
public class SampleController extends CRUDController<Long, Sample> {
   
  public SampleController(SampleDAO dao) {
    super(dao, form(Sample.class), Long.class, Sample.class, 10, "name");
  }

  protected String templateForList()
  [...]
    
	@Override
	public Result newForm()
	{
	  if(!Loggin.someoneIsLogged())
	    // Loggin.reder() must return a Return object
	    return Loggin.reder();
		return super.newForm();
	}
}
```
