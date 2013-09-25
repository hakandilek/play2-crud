package play.utils.meta;

public abstract class Converter<K> {

	private Class<K> typeClass;

	public Converter(Class<K> typeClass) {
		this.typeClass = typeClass;
	}

	public abstract K convert(String strValue);

	public Class<K> typeClass() {
		return typeClass;
	}
}
