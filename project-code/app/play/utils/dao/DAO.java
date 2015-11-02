package play.utils.dao;

import java.util.List;

public interface DAO<K, M> {
	
	List<M> all();

	K create(M c);

	void remove(K key) throws EntityNotFoundException;

	M get(K key);

	void update(M c);

	void addListener(DAOListener<K, M> l);
	
	Page<M> page(int page, int pageSize, String orderBy);
	<F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue);
	//Page<M> page(int page, int pageSize, String orderBy, String cacheKey, Expression expression);

	void saveAssociation(M c, String association);

}
