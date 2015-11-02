package play.utils.dao;

public class TimestampDAO<K, M extends TimestampModel<K>> extends DAOAdapter<K, M> implements DAO<K, M> {

	public TimestampDAO(DAO<K, M> delegate) {
		super(delegate);
		addListener(new TimestampListener<K, M>());
	}

}
