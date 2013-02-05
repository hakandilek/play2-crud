package play.utils.dao;

import java.util.Date;

public class TimestampListener<K, M extends TimestampModel<K>> implements DAOListener<K, M> {

	@Override
	public void beforeCreate(M m) {
		Date now = new Date();
		m.setCreatedOn(now);
		m.setUpdatedOn(now);
	}

	@Override
	public void afterCreate(K k, M m) {
	}

	@Override
	public void beforeRemove(K k) {
	}

	@Override
	public void afterRemove(K key, M ref) {
	}

	@Override
	public void beforeUpdate(K key, M m) {
		Date now = new Date();
		m.setUpdatedOn(now);
	}

	@Override
	public void afterUpdate(K key, M m) {
	}

}
