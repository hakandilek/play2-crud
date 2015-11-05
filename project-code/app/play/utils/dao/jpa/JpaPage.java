package play.utils.dao.jpa;

import java.util.List;

import play.utils.dao.Page;

public class JpaPage<M> implements Page<M> {

	private List<M> list;
	private int page;
	private int pageSize;

	public JpaPage(List<M> list, int page, int pageSize) {
		this.list = list;
		this.page = page;
		this.pageSize = pageSize;
	}

	@Override
	public int getPageIndex() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	@Override
	public List<M> list() {
		return list;
	}

	public int size() {
		return list != null ? list.size() : 0;
	}
	
}
