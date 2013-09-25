package play.utils.meta;

import java.lang.reflect.Field;

import javax.persistence.Id;

public class FieldMetadata {

	Field field;
	boolean key;
	Converter<?> converter;

	public FieldMetadata(Field field, Converter<?> converter) {
		this.field = field;
		this.converter = converter;
		if (field.isAnnotationPresent(Id.class))
			key = true;
	}

	public Field getField() {
		return field;
	}

	public Class<?> getType() {
		return field.getType();
	}

	public boolean isKey() {
		return key;
	}

	public Converter<?> getConverter() {
		return converter;
	}

}
