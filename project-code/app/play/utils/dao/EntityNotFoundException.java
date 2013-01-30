package play.utils.dao;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final Object key;

	public <K> EntityNotFoundException(K key) {
		super("invalid key:" + key);
		this.key = key;
	}

	public Object getKey() {
		return key;
	}


	
}
