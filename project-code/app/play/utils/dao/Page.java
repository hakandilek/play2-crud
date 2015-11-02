package play.utils.dao;

import java.util.List;

public interface Page<T> {

	int getTotalRowCount();

	int getTotalPageCount();

	int getPageIndex();

	boolean hasNext();

	boolean hasPrev();

	Page<T> next();

	Page<T> prev();

	String getDisplayXtoYofZ(String to, String of);

	List<T> list();

}
