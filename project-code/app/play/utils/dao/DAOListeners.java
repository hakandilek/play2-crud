package play.utils.dao;

import java.util.ArrayList;
import java.util.List;

public class DAOListeners<K, M> implements DAOListener<K, M>{

	private List<DAOListener<K, M>> list = new ArrayList<DAOListener<K,M>>();

	public void add(DAOListener<K, M> l) {
		list.add(l);
	}
	
	public void beforeCreate(M m) {
		for (DAOListener<K, M> l : list)
			l.beforeCreate(m);
	}

	@Override
	public void afterCreate(K k, M m) {
		for (DAOListener<K, M> l : list)
			l.afterCreate(k, m);
	}

	@Override
	public void beforeRemove(K k) {
		for (DAOListener<K, M> l : list)
			l.beforeRemove(k);
	}

	@Override
	public void afterRemove(K key, M ref) {
		for (DAOListener<K, M> l : list)
			l.afterRemove(key, ref);
	}

	@Override
	public void beforeUpdate(M m) {
		for (DAOListener<K, M> l : list)
			l.beforeUpdate(m);
	}

	@Override
	public void afterUpdate(M m) {
		for (DAOListener<K, M> l : list)
			l.afterUpdate(m);
	}
}
