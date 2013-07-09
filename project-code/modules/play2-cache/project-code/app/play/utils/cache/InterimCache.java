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

	private final String prefix;

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
		this(type.getName(), timeout);
	}
	
	/**
	 * Public constructor
	 * 
	 * @param cacheKey
	 *            unique cache key
	 * @param timeout
	 *            cache timeout
	 */
	public InterimCache(String cacheKey, int timeout) {
		this.timeout = timeout;
		this.prefix = cacheKey;
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
		String key = prefix + id;
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

	/**
	 * Creates and caches the page with the given creator
	 * 
	 * @param id
	 *            unique cache id
	 * @param creator
	 *            page creator callback object
	 * @return the cached page if one exists, otherwise creates a new one and
	 *         caches it.
	 */
	public T get(String id, Callable<T> creator) {
		String key = prefix + id;
		if (Logger.isDebugEnabled())
			Logger.debug("key : " + key);
		try {
			T t = Cache.getOrElse(key, creator, timeout);
			if (Logger.isDebugEnabled())
				Logger.debug("t : " + t);
			return t;
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return null;
		}
	}

	/**
	 * Creates and caches the page with the given creator
	 * 
	 * @param id
	 *            unique cache id
	 * @param creator
	 *            page creator callback object
	 * @return the cached page if one exists, otherwise creates a new one and
	 *         caches it.
	 */
	public void set(String id, T value) {
		String key = prefix + id;
		if (Logger.isDebugEnabled())
			Logger.debug("key : " + key);
		try {
			Cache.set(key, value);
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
		}
	}

}
