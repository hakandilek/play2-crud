package play.utils.dao;

public class DAOListenerAdapter<K, M> implements DAOListener<K, M> {

	@Override
	public void beforeCreate(M m) {
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
	public void beforeUpdate(M m) {
	}

	@Override
	public void afterUpdate(M m) {
	}

}
