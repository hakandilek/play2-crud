package play.utils.meta;

public interface KeyConverterRegistry {

	<K> KeyConverter<K> getConverter(Class<K> type);

}
