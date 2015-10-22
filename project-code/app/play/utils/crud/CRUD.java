package play.utils.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import play.mvc.Result;

import java.io.Serializable;

public interface CRUD<M, K extends Serializable> {

	JpaRepository<M, K> getRepo();

	Class<M> getModelClass();

	Result create();
	Result read(K key);
	Result update(K key);
	Result delete(K key);
	Result list();

}
