package play.utils.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.utils.dao.SimpleModel;
import play.utils.dao.DAO;
import play.utils.dao.DAOListener;
import play.utils.dao.DAOListeners;
import play.utils.dao.EntityNotFoundException;
import play.utils.dao.Page;

public class JpaDAO<K, M extends SimpleModel<K>> implements DAO<K, M> {

	private DAOListeners<K, M> listeners = new DAOListeners<K, M>();

	private Class<M> modelClass;

	public JpaDAO(Class<K> keyClass, Class<M> modelClass) {
		this.modelClass = modelClass;
	}

	public K create(M m) {
		listeners.beforeCreate(m);
		JPA.em().persist(m);
		final K key = m.getKey();
		listeners.afterCreate(key, m);
		return key;
	}

	public void remove(K key) throws EntityNotFoundException {
		listeners.beforeRemove(key);
		M ref = JPA.em().find(modelClass, key);
		if (ref == null)
			throw new EntityNotFoundException(key);
		JPA.em().remove(ref);
		listeners.afterRemove(key, ref);
	}

	public M get(K key) {
		return JPA.em().find(modelClass, key);
	}

	public void update(M m) {
		listeners.beforeUpdate(m);
		m = JPA.em().merge(m);
		listeners.afterUpdate(m);
	}

	@Override
	public void addListener(DAOListener<K, M> l) {
		listeners.add(l);
	}

	@Override
	public List<M> all() {
		CriteriaQuery<M> query = query();
		return list(query);
	}

	@Override
	public void saveAssociation(M c, String association) {
		// TODO save associations
	}


	@Override
	public <F> Page<M> page(int page, int pageSize, String orderBy, String filterField, F filterValue) {
		TypedQuery<M> query = query(page, pageSize, orderBy, filterField, filterValue);
		List<M> list = list(query);
		return new JpaPage<M>(list, page, pageSize);
	}

	private <F> TypedQuery<M> query(int page, int pageSize, String orderBy, String filterField, F filterValue) {
		CriteriaQuery<M> cq = orderBy(orderBy);
		if (filterValue != null && !"".equals(filterValue)) {
			CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
			Root<M> root = cq.from(modelClass);
			Expression<String> field = root.get(filterField);
			cq = cq.where(cb.equal(field, filterValue));
		}
		TypedQuery<M> tq = JPA.em().createQuery(cq);
		tq = tq.setMaxResults(pageSize).setFirstResult(pageSize * page);
		return tq;
	}

	@Transactional(readOnly=true)
	@Override
	public Page<M> page(int page, int pageSize, String orderBy) {
		TypedQuery<M> query = query(page, pageSize, orderBy);
		List<M> list = list(query);
		return new JpaPage<M>(list, page, pageSize);
	}

	protected TypedQuery<M> query(int page, int pageSize, String orderBy) {
		CriteriaQuery<M> cq = orderBy(orderBy);
		TypedQuery<M> tq = JPA.em().createQuery(cq);
		tq = tq.setMaxResults(pageSize).setFirstResult(pageSize * page);
		return tq;
	}

	protected CriteriaQuery<M> orderBy(String orderBy) {
		CriteriaQuery<M> cq= query();
		if (orderBy != null && !"".equals(orderBy)) {
			CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
			Root<M> root = cq.from(modelClass);
			
			if (orderBy.startsWith("-")) {
				orderBy = orderBy.substring(1).trim();
				cq = cq.orderBy(cb.desc(root.get(orderBy)));
			} else if (orderBy.endsWith("desc")) {
				orderBy = orderBy.substring(0, orderBy.length()-5).trim();
				cq = cq.orderBy(cb.desc(root.get(orderBy)));
			}else if (orderBy.startsWith("+")) {
				orderBy = orderBy.substring(1).trim();
				cq = cq.orderBy(cb.asc(root.get(orderBy)));
			} else if (orderBy.endsWith("asc")) {
				orderBy = orderBy.substring(0, orderBy.length()-4).trim();
				cq = cq.orderBy(cb.asc(root.get(orderBy)));
			} else {
				orderBy = orderBy.trim();
				cq = cq.orderBy(cb.asc(root.get(orderBy)));
			}
		}
		return cq;
	}

	protected CriteriaQuery<M> query() {
		EntityManager em = JPA.em();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<M> cq = cb.createQuery(modelClass);
		return cq;
	}
	

	protected List<M> list(CriteriaQuery<M> query) {
		EntityManager em = JPA.em();
		TypedQuery<M> q = em.createQuery(query);
		return q.getResultList();
	}

	protected List<M> list(TypedQuery<M> query) {
		return query.getResultList();
	}

}
