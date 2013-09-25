package play.utils.meta.convert;

import play.utils.meta.Converter;

public class DoubleConverter extends Converter<Double> {

	public DoubleConverter() {
		super(Double.class);
	}

	@Override
	public Double convert(String key) {
		return Double.valueOf(key);
	}

}
