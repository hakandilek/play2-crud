package play.utils.crud;

import play.mvc.Result;
import play.utils.dao.DAO;

public interface CRUD<K, M> {

	DAO<K, M> getDao();

	Class<M> getModelClass();

	Result create();
	Result read(K key);
	Result update(K key);
	Result delete(K key);
	Result list();

}
