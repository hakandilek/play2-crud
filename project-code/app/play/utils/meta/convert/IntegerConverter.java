package play.utils.meta.convert;

import play.utils.meta.Converter;

public class IntegerConverter extends Converter<Integer> {

	public IntegerConverter() {
		super(Integer.class);
	}

	@Override
	public Integer convert(String key) {
		return Integer.valueOf(key);
	}

}
