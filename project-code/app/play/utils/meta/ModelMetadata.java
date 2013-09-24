package play.utils.meta;

import java.util.Map;

public class ModelMetadata {

	Class<?> type;

	FieldMetadata keyField;

	Map<String, FieldMetadata> fields;

	KeyConverter<?> keyConverter;

	public ModelMetadata(Class<?> type, Map<String, FieldMetadata> allFields, FieldMetadata keyField, KeyConverter<?> keyConverter) {
		this.type = type;
		this.fields = allFields;
		this.keyField = keyField;
		this.keyConverter = keyConverter;
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

	public KeyConverter<?> getKeyConverter() {
		return keyConverter;
	}

}
