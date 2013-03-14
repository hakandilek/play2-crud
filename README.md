play2-crud
==========

simple CRUD &amp; DAO implementation for [play framework](http://github.com/playframework/play) 2.0 and 2.1

# Sample
   
   [Here](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-crud-sample) is a full featured sample.
   
# HOW-TO

Follow these steps to use play2-crud. You can also use it partially just for DAO or CRUD controllers

## Define model
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

## Define DAO

```java
public class SampleDAO extends BasicDAO<Long, Sample> {
   public SampleDAO() {
      super(Long.class, Sample.class);
   }
}
```

## Define Controller

```java
public class SampleController extends CRUDController<Long, Sample> {
   
   public SampleController(SampleDAO dao) {
      super(dao, form(Sample.class), Long.class, Sample.class);
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

