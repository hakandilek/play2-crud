package play.utils.meta.convert;

import play.utils.meta.KeyConverter;

public class LongConverter extends KeyConverter<Long> {

	public LongConverter() {
		super(Long.class);
	}

	@Override
	public Long convert(String key) {
		return Long.valueOf(key);
	}

}
