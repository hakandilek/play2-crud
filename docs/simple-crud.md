## HOW-TO use simple CRUD

Follow these steps to use play2-crud. You can also use it partially just for DAO or CRUD controllers. If you think any part needs further explanation, please report a new issue.

### Define model

 * Model class has to implement `play.utils.dao.ActiveObjectModel` with the type parameter indicating the type of the `@Id` field.

```java
@Entity
public class Sample extends Model implements ActiveObjectModel<Long> {

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

 * Here the `Sample` model class implements `ActiveObjectModel<Long>` where `key` field indicated with `@Id` is `Long`.

### Associate Global settings

 * Change the `application.global` configuration key in the `conf/application.conf` file, aand use `play.utils.crud.GlobalCRUDSettings`:

```
...

application.global=play.utils.crud.GlobalCRUDSettings

...

```

 * If the above key is commented, you can just uncomment the configuration line, and change it.
 * You can also use a class which is extending `play.utils.crud.GlobalCRUDSettings`.

### Define routes

```

# CRUD Controllers
->     /app             play.crud.Routes

# REST API
->     /api             play.rest.Routes

```

... and voila!

