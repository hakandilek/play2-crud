package play.utils.meta.convert;


public class LongConverter extends Converter<Long> {

	public LongConverter() {
		super(Long.class);
	}

	@Override
	public Long convert(String key) {
		return Long.valueOf(key);
	}

}
