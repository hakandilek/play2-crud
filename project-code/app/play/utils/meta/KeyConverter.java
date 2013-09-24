package play.utils.meta;

public abstract class KeyConverter<K> {

	private Class<K> keyClass;

	public KeyConverter(Class<K> keyClass) {
		this.keyClass = keyClass;
	}

	public abstract K convert(String key);

	public Class<K> keyClass() {
		return keyClass;
	}
}
