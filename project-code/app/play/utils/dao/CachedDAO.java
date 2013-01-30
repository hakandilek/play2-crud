package play.utils.dao;

import java.util.List;

import play.db.ebean.Model.Finder;
import play.utils.cache.CachedFinder;

public class CachedDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final CachedFinder<K, M> find;

	public CachedDAO(Class<K> keyClass, Class<M> modelClass) {
		super();
		this.find = new CachedFinder<K, M>(keyClass, modelClass);
	}

	public List<M> all() {
		return find.all();
	}

	public K create(M m) {
		m.save();
		final K key = m.getKey();
		find.put(key, m);
		return key;
	}

	public void remove(K key) throws EntityNotFoundException {
		M ref = find.ref(key);
		if (ref == null) throw new EntityNotFoundException(key);
		ref.delete();
		find.clean(key);
	}

	public M get(K key) {
		return find.byId(key);
	}

	public void update(K key, M m) {
		m.update(key);
		find.put(m.getKey(), m);
	}

	public Finder<K, M> find() {
		return find;
	}

}
