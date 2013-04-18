package play.utils.dao;

public interface DAOListener<K, M> {

	void beforeCreate(M m);
	void afterCreate(K k, M m);
	void beforeRemove(K k);
	void afterRemove(K key, M ref);
	void beforeUpdate(M m);
	void afterUpdate(M m);

}
