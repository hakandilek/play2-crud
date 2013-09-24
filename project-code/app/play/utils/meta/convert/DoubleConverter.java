package play.utils.meta.convert;

import play.utils.meta.KeyConverter;

public class DoubleConverter extends KeyConverter<Double> {

	public DoubleConverter() {
		super(Double.class);
	}

	@Override
	public Double convert(String key) {
		return Double.valueOf(key);
	}

}
