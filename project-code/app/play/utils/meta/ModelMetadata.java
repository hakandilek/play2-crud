package play.utils.meta;

import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class ModelMetadata {

	Class<?> type;

	FieldMetadata keyField;

	Map<String, FieldMetadata> fields;

	public ModelMetadata(Class<?> type, Map<String, FieldMetadata> fields) {
		this.type = type;
		this.fields = fields;
		this.keyField = Iterables.find(fields.values(), new Predicate<FieldMetadata>() {
			@Override
			public boolean apply(FieldMetadata fieldInfo) {
				return fieldInfo.isKey();
			}
		});
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

}
