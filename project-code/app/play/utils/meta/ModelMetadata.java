package play.utils.meta;

import java.lang.reflect.Field;
import java.util.Map;

public class ModelMetadata {

	Class<?> type;

	FieldMetadata keyField;

	Map<String, FieldMetadata> fields;

	public ModelMetadata(Class<?> type, Map<String, FieldMetadata> allFields, FieldMetadata keyField) {
		this.type = type;
		this.fields = allFields;
		this.keyField = keyField;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return type.getSimpleName();
	}

	public FieldMetadata getKeyField() {
		return keyField;
	}

	public Map<String, FieldMetadata> getFields() {
		return fields;
	}

	public Converter<?> getKeyConverter() {
		return keyField.getConverter();
	}

	public <M> void setField(M modelObject, String fieldName, String valueStr) throws IllegalArgumentException, IllegalAccessException {
		FieldMetadata fieldMeta = fields.get(fieldName);
		Converter<?> converter = fieldMeta.getConverter();
		Field field = fieldMeta.getField();
		Object value = converter.convert(valueStr);
		field.set(modelObject, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModelMetadata [").append(type).append("]");
		return builder.toString();
	}

}
