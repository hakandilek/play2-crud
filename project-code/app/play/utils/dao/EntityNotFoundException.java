package play.utils.dao;

import javax.persistence.PersistenceException;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final Object key;

	public <K> EntityNotFoundException(K key) {
		super("invalid key:" + key);
		this.key = key;
	}

	public <K> EntityNotFoundException(K key, PersistenceException reason) {
		super("invalid key:" + key, reason);
		this.key = key;
	}

	public Object getKey() {
		return key;
	}


	
}
