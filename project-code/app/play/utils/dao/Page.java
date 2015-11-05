package play.utils.dao;

import java.util.List;

public interface Page<T> {

	int getPageIndex();

	List<T> list();
	
	int size();

}
