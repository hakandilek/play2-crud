package play.utils.dao;

import java.util.List;

import play.db.ebean.Model.Finder;

public class BasicDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final Finder<K, M> find;

	public BasicDAO(Class<K> keyClass, Class<M> modelClass) {
		super();
		this.find = new Finder<K, M>(keyClass, modelClass);
	}

	public List<M> all() {
		return find.all();
	}

	public K create(M m) {
		m.save();
		final K key = m.getKey();
		return key;
	}

	public void remove(K key) throws EntityNotFoundException {
		M ref = find.ref(key);
		if (ref == null) throw new EntityNotFoundException(key);
		ref.delete();
	}

	public M get(K key) {
		return find.byId(key);
	}

	public void update(K key, M m) {
		m.update(key);
	}

	public Finder<K, M> find() {
		return find;
	}

}
