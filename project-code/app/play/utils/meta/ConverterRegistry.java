package play.utils.meta;

import play.utils.meta.convert.Converter;

public interface ConverterRegistry {

	<K> Converter<K> getConverter(Class<K> type);

}
