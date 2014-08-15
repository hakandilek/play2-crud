
## HOW-TO Define Custom Controller

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
