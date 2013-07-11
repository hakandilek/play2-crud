package play.utils.meta;

import java.lang.reflect.Field;

import javax.persistence.Id;

public class FieldMetadata {

	Field field;
	boolean key;

	public FieldMetadata(Field field) {
		this.field = field;
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

}
