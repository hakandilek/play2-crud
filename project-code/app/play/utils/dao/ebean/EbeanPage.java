package play.utils.dao.ebean;

import java.util.List;

import play.utils.dao.Page;

public class EbeanPage<M> implements Page<M> {

	private com.avaje.ebean.Page<M> page;

	public EbeanPage(com.avaje.ebean.Page<M> page) {
		this.page = page;
	}

	public int getTotalRowCount() {
		return page.getTotalRowCount();
	}

	public int getTotalPageCount() {
		return page.getTotalPageCount();
	}

	@Override
	public int getPageIndex() {
		return page.getPageIndex();
	}

	public boolean hasNext() {
		return page.hasNext();
	}

	public boolean hasPrev() {
		return page.hasPrev();
	}

	@Override
	public List<M> list() {
		return page.getList();
	}

	public Page<M> next() {
		return new EbeanPage<>(page.next());
	}

	public Page<M> prev() {
		return new EbeanPage<>(page.prev());
	}

	public String getDisplayXtoYofZ(String to, String of) {
		return page.getDisplayXtoYofZ(to, of);
	}
	
	public int size() {
		List<M> list = page.getList();
		return list != null ? list.size() : 0;
	}


}
