package play.utils.dao.ebean;

import java.util.List;

import javax.persistence.PersistenceException;

import play.utils.dao.BasicModel;
import play.utils.dao.DAO;
import play.utils.dao.DAOListener;
import play.utils.dao.DAOListeners;
import play.utils.dao.EntityNotFoundException;
import play.utils.dao.Page;

public class CachedEbeanDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final CachedEbeanFinder<K, M> find;

	private DAOListeners<K, M> listeners = new DAOListeners<K, M>();

	public CachedEbeanDAO(Class<K> keyClass, Class<M> modelClass) {
		super();
		this.find = new CachedEbeanFinder<K, M>(keyClass, modelClass);
	}

	public List<M> all() {
		return find.all();
	}

	public K create(M m) {
		listeners.beforeCreate(m);
		m.save();
		final K key = m.getKey();
		find.put(key, m);
		listeners.afterCreate(key, m);
		return key;
	}

	public void remove(K key) throws EntityNotFoundException {
		listeners.beforeRemove(key);
		M ref = find.ref(key);
		if (ref == null) throw new EntityNotFoundException(key);
		try {
			ref.delete();
		} catch (PersistenceException e) {
			throw new EntityNotFoundException(key, e);
		}
		find.clean(key);
		listeners.afterRemove(key, ref);
	}

	public M get(K key) {
		return find.byId(key);
	}

	public void update(M m) {
		listeners.beforeUpdate( m);
		m.update();
		find.put(m.getKey(), m);
		listeners.afterUpdate(m);
	}

	protected CachedEbeanFinder<K, M> find() {
		return find;
	}

	@Override
	public void addListener(DAOListener<K, M> l) {
		listeners.add(l);
	}

	@Override
	public Page<M> page(int page, int pageSize, String orderBy) {
		return new EbeanPage<>(find.page(page, pageSize, orderBy));
	}

	@Override
	public <F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue) {
		return new EbeanPage<>(find.page(page, pageSize, orderBy, filterField, filterValue));
	}

	/*
	public Page<M> page(int page, int pageSize, String orderBy,
			String cacheKey, Expression expression) {
		return find.page(page, pageSize, orderBy, cacheKey, expression);
	}
	*/

	@Override
	public void saveAssociation(M c, String association) {
		c.saveManyToManyAssociations(association);
		K key = c.getKey();
		find().clean(key);
	}
}
