package play.utils.dao;

import java.util.List;

import com.avaje.ebean.Expression;
import com.avaje.ebean.Page;

import play.db.ebean.Model.Finder;

public class BasicDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final Finder<K, M> find;
	
	private Listeners<K, M> listeners = new Listeners<K, M>();

	public BasicDAO(Finder<K, M> finder) {
		super();
		this.find = finder;
	}
	public BasicDAO(Class<K> keyClass, Class<M> modelClass) {
		this(new Finder<K, M>(keyClass, modelClass));
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

	public void update( M m) {
		listeners.beforeUpdate( m);
		m.update();
		listeners.afterUpdate( m);
	}

	protected Finder<K, M> find() {
		return find;
	}

	@Override
	public void addListener(DAOListener<K, M> l) {
		listeners.add(l);
	}
	
	@Override
	public Page<M> page(int page, int pageSize, String orderBy) {
		return find.where().orderBy(orderBy).findPagingList(pageSize)
				.getPage(page);
	}
	
	@Override
	public <F> Page<M> page(int page, int pageSize, String orderBy,
			String filterField, F filterValue) {
		return find.where().eq(filterField, filterValue)
				.orderBy(orderBy).findPagingList(pageSize)
				.getPage(page);
	}

	public Page<M> page(int page, int pageSize, String orderBy,
			String cacheKey, Expression expression) {
		return find.where().add(expression)
				.orderBy(orderBy).findPagingList(pageSize)
				.getPage(page);
	}
	@Override
	public void saveAssociation(M c, String association) {
		c.saveManyToManyAssociations(association);
	}

}
