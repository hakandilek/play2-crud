play2-crud
==========

Powerful CRUD &amp; DAO implementation with REST interface for [play framework](http://github.com/playframework/play) 2.0 and 2.1

Play 2.2 support is coming soon!

# Samples
   
   * [Sample with basic dynamic CRUD controllers](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-crud-simple)
   * [Sample with custom views](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-crud-customView) is a full featured sample.
   * [Full featured sample with DAO and DAOListeners](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-crud-sample)
   * [Sample with Cache usage](https://github.com/hakandilek/play2-crud/tree/master/samples/play2-cache-sample)
 
## Quick Start

Follow these steps to use play2-crud. You can also use it partially just for DAO or CRUD controllers. If you think any part needs further explanation, please report a new issue.

### Add play2-crud dependency

You can begin with adding play2-crud dependency inside `conf/Build.scala` file.

 * Add app dependency:

```
    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean,
        "play2-crud" % "play2-crud_2.10" % "0.7.0"
    )

```

 * Here in the sample, it is defined for version 0.7.0, but you can use the latest version accordingly.

 * Add custom maven repositories:

```
    val main = play.Project(appName, appVersion, appDependencies).settings(
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"
    )

```

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


### Define model

 * Model class has to implement `play.utils.dao.BasicModel` with the type parameter indicating the type of the `@Id` field.

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

... call [http://localhost:9000/app](http://localhost:9000/app) and voila!

### Some screenshots

 * index page
   ![crud-index page](/screenshot/index.png)

 * create page
   ![create page](/screenshot/create.png)

 * list page
   ![list page](/screenshot/list.png)
   
# HOW-TO

 Here you can find some HOW-TO documents introducing some powerful functionality:
  * [HOW-TO use simple CRUD](docs/simple-crud.md)
  * [HOW-TO define a custom DAO](docs/custom-dao.md)
  * [HOW-TO define a custom Controller](docs/custom-controller.md)
  * [HOW-TO use DAO Listeners](docs/dao-listeners.md)
  * [HOW-TO use dynamic REST Controllers](docs/rest-controllers.md)
  * [HOW-TO use custom REST Controllers](docs/custom-rest-controllers.md)

