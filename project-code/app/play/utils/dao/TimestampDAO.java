package play.utils.dao;


public class TimestampDAO<K, M extends TimestampModel<K>> extends BasicDAO<K, M> {

	public TimestampDAO(Class<K> keyType, Class<M> modelType) {
		super(keyType, modelType);
		addListener(new TimestampListener<K, M>());
	}

}
