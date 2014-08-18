
## HOW-TO Define Custom DAO

###Definition
DAO stands for Data Access Object. This object is call for the interaction with the database. It overrides the create, update and delete functions.
###Creation
DAO class extends `play.utils.dao.BasicDAO` with two type parameters: One for the key type and one for the Model type and overrides the super class constructor with `super(Long.class, Sample.class)`
Example:
```java
public class SampleDAO extends BasicDAO<Long, Sample> {
   public SampleDAO() {
      super(Long.class, Sample.class);
   }
}
```
Here the `SampleDAO` extends `BasicDAO<Long, Sample>` with `Long` key type, and `Sample` model type.

Note that a `@Inject` (javax.inject.Inject) annotation may be required before the constructor definition.
###Overriding DAO functions
The interest of having DAO objects is that you can override their functions. This is the functions of the DAO that can be overrided :
```java
public interface DAO<K, M> {
	List<M> all();

	K create(M c);

	void remove(K key) throws EntityNotFoundException;

	M get(K key);

	void update(M c);

	void addListener(DAOListener<K, M> l);
	
	Page<M> page(int page, int pageSize, String orderBy);
	<F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue);
	Page<M> page(int page, int pageSize, String orderBy, String cacheKey, Expression expression);

	void saveAssociation(M c, String association);
}
```
K being the key type and M the Model type.

Example: 
Let's say you want to make sure that the randomValue is not higher than 10 before you save it to your database. The code is :
```java
public class SampleDAO extends BasicDAO<Long, Sample> {
   public SampleDAO() {
      super(Long.class, Sample.class);
   }

	@Override
	public Long create(Sample sam)
	{
	   if(sam.randomValue > 10)
	      sam = 10;
		return super.create(m);
	}
}

```
###DAO for cached data
Alternatively, `play.utils.dao.CachedDAO` can be used to cache fetched data.
Classes implementing both `BasicDAO` or `CachedDAO` should override the constructor with key ad model class type parameters.
