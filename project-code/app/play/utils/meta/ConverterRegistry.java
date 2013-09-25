package play.utils.meta;

public interface ConverterRegistry {

	<K> Converter<K> getConverter(Class<K> type);

}
