package play.utils.dao;

import java.util.Date;
import java.util.List;

import play.db.ebean.Model.Finder;

public class TimestampDAO<K, M extends TimestampModel<K>> implements DAO<K, M> {

	private DAO<K, M> delegateDAO;

	public TimestampDAO(DAO<K, M> delegate) {
		super();
		this.delegateDAO = delegate;
	}

	public List<M> all() {
		return delegateDAO.all();
	}

	public K create(M m) {
		Date now = new Date();
		m.setCreatedOn(now);
		m.setUpdatedOn(now);
		return delegateDAO.create(m);
	}

	public void remove(K key) throws EntityNotFoundException {
		delegateDAO.remove(key);
	}

	public M get(K key) {
		return delegateDAO.get(key);
	}

	public void update(K key, M m) {
		Date now = new Date();
		m.setUpdatedOn(now);
		delegateDAO.update(key, m);
	}
	
	public Finder<K,M> find() {
		return delegateDAO.find();
	}

}
