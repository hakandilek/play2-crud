
## HOW-TO Define Custom DAO

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
 

