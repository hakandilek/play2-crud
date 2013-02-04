package play.utils.dao;

import java.util.List;

import play.db.ebean.Model.Finder;

public class BasicDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final Finder<K, M> find;
	
	private Listeners<K, M> listeners = new Listeners<K, M>();

	public BasicDAO(Class<K> keyClass, Class<M> modelClass) {
		super();
		this.find = new Finder<K, M>(keyClass, modelClass);
	}

	public List<M> all() {
		return find.all();
	}

	public K create(M m) {
		listeners.beforeCreate(m);
		m.save();
		final K key = m.getKey();
		listeners.afterCreate(key, m);
		return key;
	}

	public void remove(K key) throws EntityNotFoundException {
		listeners.beforeRemove(key);
		M ref = find.ref(key);
		if (ref == null) throw new EntityNotFoundException(key);
		ref.delete();
		listeners.afterRemove(key, ref);
	}

	public M get(K key) {
		return find.byId(key);
	}

	public void update(K key, M m) {
		listeners.beforeUpdate(key, m);
		m.update(key);
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
