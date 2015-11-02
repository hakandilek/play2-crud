package play.utils.dao.ebean;

import java.util.List;

import play.utils.dao.Page;

public class EbeanPage<M> implements Page<M> {

	private com.avaje.ebean.Page<M> page;

	public EbeanPage(com.avaje.ebean.Page<M> page) {
		this.page = page;
	}

	@Override
	public int getTotalRowCount() {
		return page.getTotalRowCount();
	}

	@Override
	public int getTotalPageCount() {
		return page.getTotalPageCount();
	}

	@Override
	public int getPageIndex() {
		return page.getPageIndex();
	}

	@Override
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	public boolean hasPrev() {
		return page.hasPrev();
	}

	@Override
	public List<M> list() {
		return page.getList();
	}

	@Override
	public Page<M> next() {
		return new EbeanPage<>(page.next());
	}

	@Override
	public Page<M> prev() {
		return new EbeanPage<>(page.prev());
	}

	@Override
	public String getDisplayXtoYofZ(String to, String of) {
		return page.getDisplayXtoYofZ(to, of);
	}

}
