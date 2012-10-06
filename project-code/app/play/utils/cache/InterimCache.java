package play.utils.cache;

import java.util.concurrent.Callable;

import play.Logger;
import play.Logger.ALogger;
import play.cache.Cache;

import com.avaje.ebean.Page;

/**
 * 
 * @author hakandilek
 * 
 * @param <T>
 *            type of cache
 */
public class InterimCache<T> {

	private static ALogger log = Logger.of(InterimCache.class);

	private final String pre;

	private final int timeout;

	/**
	 * Public constructor
	 * 
	 * @param type
	 *            type of objects contained in this cache
	 * @param timeout
	 *            cache timeout
	 */
	public InterimCache(Class<T> type, int timeout) {
		this.timeout = timeout;
		this.pre = type.getName();
	}

	/**
	 * Creates and caches the page with the given creator
	 * 
	 * @param id
	 *            unique cache id
	 * @param pageCreator
	 *            page creator callback object
	 * @return the cached page if one exists, otherwise creates a new one and
	 *         caches it.
	 */
	public Page<T> page(String id, Callable<Page<T>> pageCreator) {
		String key = pre + id;
		if (Logger.isDebugEnabled())
			Logger.debug("key : " + key);
		try {
			Page<T> p = Cache.getOrElse(key, pageCreator, timeout);
			if (Logger.isDebugEnabled())
				Logger.debug("p : " + p);
			return p;
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return null;
		}
	}

}
