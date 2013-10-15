
## HOW-TO use DAO Listeners

This part introduces how to use DAO listeners

### Additions to the model 

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

### Create a new `DAOListener`

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

### Register the `DAOListener` to the `DAO`

 * Add the `SampleDAOListener` in `SampleDAO` constructor: 
 
 
```java
   public SampleDAO() {
		super(Long.class, Sample.class);
		addListener(new SampleDAOListener());
	}
```

