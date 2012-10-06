package play.utils.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import play.Logger;
import play.Logger.ALogger;
import play.cache.Cache;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.Page;

public class CachedFinder<K, T> extends Finder<K, T> {

	private static ALogger log = Logger.of(CachedFinder.class);

	/** expire in 24 hours */
	private static final int EXPIRATION = 24 * 3600;

	/** serial id */
	private static final long serialVersionUID = 1L;

	/** prefix for all keys */
	private final String pre;

	/** prefix for page keys */
	private final String prePage;

	/** key for retrieving all entities */
	private final String keyAll;
	
	/** cache keys for cached pages */
	private final Set<String> pages = new HashSet<String>();

	public CachedFinder(Class<K> keyType, Class<T> type) {
		super(keyType, type);
		this.pre = type.getName();
		keyAll = pre + ".all";
		prePage = pre + ".page.";
	}

	public CachedFinder(String serverName, Class<K> keyType, Class<T> type) {
		super(serverName, keyType, type);
		this.pre = type.getName();
		keyAll = pre + ".all";
		prePage = pre + ".page.";
	}

	@Override
	public T byId(final K id) {
		String key = pre + id;
		try {
			return Cache.getOrElse(key, new Callable<T>() {
				public T call() throws Exception {
					return CachedFinder.super.byId(id);
				}
			}, EXPIRATION);
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return super.byId(id);
		}
	}

	@Override
	public T ref(final K id) {
		String key = pre + id;
		try {
			return Cache.getOrElse(key, new Callable<T>() {
				public T call() throws Exception {
					return CachedFinder.super.ref(id);
				}
			}, EXPIRATION);
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return super.ref(id);
		}
	}

	@SuppressWarnings("unchecked")
	public T clean(final K id) {
		String key = pre + id;
		T t = null;
		try {
			t = (T) Cache.get(key);
		} catch (Exception e) {
			log.debug("exception occured while retrieving from cache", e);
		}
		//clean self entry
		Cache.set(key, null);
		if (t != null)
			cleanBulkCaches();
		return t;
	}

	public void put(K id, T t) {
		String key = pre + id;
		Cache.set(key, t);
		if (t != null)
			cleanBulkCaches();
	}

	@Override
	public List<T> all() {
		try {
			return Cache.getOrElse(keyAll, new Callable<List<T>>() {
				public List<T> call() throws Exception {
					return CachedFinder.super.all();
				}
			}, EXPIRATION);
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return super.all();
		}
	}

	public Page<T> page(final int page, final int pageSize, final String orderBy) {
		String key = prePage + page;
		try {
			Page<T> p = Cache.getOrElse(key, new Callable<Page<T>>() {
				public Page<T> call() throws Exception {
					return where().orderBy(orderBy).findPagingList(pageSize)
							.getPage(page);
				}
			}, EXPIRATION);
			pages.add(key);
			return p;
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return where().orderBy(orderBy).findPagingList(pageSize)
					.getPage(page);
		}
	}

	public <F> Page<T> page(final int page, final int pageSize, final String orderBy, final String filterField, final F filterValue) {
		String key = prePage + page + "." + filterField + "." + filterValue;
		try {
			Page<T> p = Cache.getOrElse(key, new Callable<Page<T>>() {
				public Page<T> call() throws Exception {
					return where().eq(filterField, filterValue)
							.orderBy(orderBy).findPagingList(pageSize)
							.getPage(page);
				}
			}, EXPIRATION);
			pages.add(key);
			return p;
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return where().eq(filterField, filterValue).orderBy(orderBy)
					.findPagingList(pageSize).getPage(page);
		}
	}
	
	protected void cleanBulkCaches() {
		//clean other entries
		Cache.set(keyAll, null);
		//clean all pages
		for (String page : pages) {
			Cache.set(page, null);
		}
		pages.clear();
	}


}
