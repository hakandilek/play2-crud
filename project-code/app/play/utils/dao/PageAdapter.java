package play.utils.dao;

public abstract class PageAdapter<S, T> implements Page<T> {

	protected Page<S> delegate;

	public PageAdapter(Page<S> delegate) {
		this.delegate = delegate;
	}

	/**
	 * created a new page adapter. used to create prev/next pages
	 * @param delegate page delegate
	 * @return a new page adapter
	 */
	public abstract PageAdapter<S, T> create(Page<S> delegate);
	
	@Override
	public int getTotalRowCount() {
		return delegate.getTotalRowCount();
	}

	@Override
	public int getTotalPageCount() {
		return delegate.getTotalPageCount();
	}

	@Override
	public int getPageIndex() {
		return delegate.getPageIndex();
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public boolean hasPrev() {
		return delegate.hasPrev();
	}

	@Override
	public Page<T> next() {
		Page<S> p = delegate.next();
		return create(p);
	}

	@Override
	public Page<T> prev() {
		Page<S> p = delegate.prev();
		return create(p);
	}

	@Override
	public String getDisplayXtoYofZ(String to, String of) {
		return delegate.getDisplayXtoYofZ(to, of);
	}

}
