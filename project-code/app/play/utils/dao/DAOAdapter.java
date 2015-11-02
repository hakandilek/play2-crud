package play.utils.dao;

import java.util.List;

public class DAOAdapter<K, M> implements DAO<K, M> {

	DAO<K, M> delegate;
	
	public DAOAdapter(DAO<K, M> delegate) {
		this.delegate = delegate;
	}

	@Override
	public List<M> all() {
		return delegate.all();
	}

	@Override
	public K create(M c) {
		return delegate.create(c);
	}

	@Override
	public void remove(K key) throws EntityNotFoundException {
		delegate.remove(key);
	}

	@Override
	public M get(K key) {
		return delegate.get(key);
	}

	@Override
	public void update(M c) {
		delegate.update(c);
	}

	@Override
	public void addListener(DAOListener<K, M> l) {
		delegate.addListener(l);
	}

	@Override
	public Page<M> page(int page, int pageSize, String orderBy) {
		return delegate.page(page, pageSize, orderBy);
	}

	@Override
	public <F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue) {
		return delegate.page(page, pageSize, orderBy, filterField, filterValue);
	}

	@Override
	public void saveAssociation(M c, String association) {
		delegate.saveAssociation(c, association);
	}

}
