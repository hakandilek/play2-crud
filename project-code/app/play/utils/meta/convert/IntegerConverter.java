package play.utils.meta.convert;

import play.utils.meta.KeyConverter;

public class IntegerConverter extends KeyConverter<Integer> {

	public IntegerConverter() {
		super(Integer.class);
	}

	@Override
	public Integer convert(String key) {
		return Integer.valueOf(key);
	}

}
