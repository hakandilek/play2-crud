package play.utils.crud;

import play.utils.dao.DAO;

public interface CRUD<K, M> {

	DAO<K, M> getDao();

	Class<K> getKeyClass();

	Class<M> getModelClass();

}
