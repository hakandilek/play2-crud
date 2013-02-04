package play.utils.dao;

import java.util.List;

import play.db.ebean.Model.Finder;
import play.utils.cache.CachedFinder;

public class CachedDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final CachedFinder<K, M> find;

	private Listeners<K, M> listeners = new Listeners<K, M>();

	public CachedDAO(Class<K> keyClass, Class<M> modelClass) {
		super();
		this.find = new CachedFinder<K, M>(keyClass, modelClass);
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
		ref.delete();
		find.clean(key);
		listeners.afterRemove(key, ref);
	}

	public M get(K key) {
		return find.byId(key);
	}

	public void update(K key, M m) {
		listeners.beforeUpdate(key, m);
		m.update(key);
		find.put(m.getKey(), m);
		listeners.afterUpdate(key, m);
	}

	public Finder<K, M> find() {
		return find;
	}

	@Override
	public void addListener(DAOListener<K, M> l) {
		listeners.add(l);
	}

}
