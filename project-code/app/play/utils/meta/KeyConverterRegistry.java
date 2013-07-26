package play.utils.meta;

public interface KeyConverterRegistry {

	KeyConverter getConverter(Class<?> type);

}
