package play.utils.dao.ebean;

import java.util.List;

import play.db.ebean.Model.Finder;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;
import play.utils.dao.DAOListener;
import play.utils.dao.EntityNotFoundException;
import play.utils.dao.Page;
import play.utils.dao.DAOListeners;

public class EbeanDAO<K, M extends BasicModel<K>> implements DAO<K, M> {

	protected final Finder<K, M> find;
	
	private DAOListeners<K, M> listeners = new DAOListeners<K, M>();

	public EbeanDAO(Finder<K, M> finder) {
		super();
		this.find = finder;
	}
	public EbeanDAO(Class<K> keyClass, Class<M> modelClass) {
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
		com.avaje.ebean.Page<M> pg = find.where().orderBy(orderBy).findPagingList(pageSize).getPage(page);
		EbeanPage<M> ebeanPage = new EbeanPage<M>(pg);
		return ebeanPage;
	}

	@Override
	public <F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue) {
		com.avaje.ebean.Page<M> pg = find.where().eq(filterField, filterValue).orderBy(orderBy).findPagingList(pageSize).getPage(page);
		EbeanPage<M> ebeanPage = new EbeanPage<M>(pg);
		return ebeanPage;
	}

	/*
	public Page<M> page(int page, int pageSize, String orderBy,
			String cacheKey, Expression expression) {
		return find.where().add(expression)
				.orderBy(orderBy).findPagingList(pageSize)
				.getPage(page);
	}
	*/
	
	@Override
	public void saveAssociation(M c, String association) {
		c.saveManyToManyAssociations(association);
	}

}
