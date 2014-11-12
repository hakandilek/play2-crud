package play.utils.meta;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.joda.time.DateTime;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.utils.field.IgnoreEdit;
import play.utils.meta.convert.Converter;
import play.utils.meta.form.CheckboxWidget;
import play.utils.meta.form.DateWidget;
import play.utils.meta.form.FileWidget;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.NumberWidget;
import play.utils.meta.form.SelectWidget;
import play.utils.meta.form.TextAreaWidget;
import play.utils.meta.form.TextWidget;

public class FieldMetadata {

	Field field;
	boolean key;
	boolean required;
	boolean sortable;
	boolean ignoreEdit;
	Converter<?> converter;
	FormFieldWidget widget;

	public FieldMetadata(Field field, Converter<?> converter) {
		this.field = field;
		this.converter = converter;

		if (annotation(Id.class) != null)
			key = true;

		if (annotation(Required.class) != null) {
			required = true;
		}

		if (annotation(IgnoreEdit.class) != null) {
			ignoreEdit = true;
		}
		
		if (CharSequence.class.isAssignableFrom(getField().getType())) {
			widget = new TextWidget(this);
			MaxLength maxLength = annotation(MaxLength.class);
			if (maxLength != null && maxLength.value() > 128)
				widget = new TextAreaWidget(this);

			Column column = annotation(Column.class);
			if (column != null && column.length() > 128)
				widget = new TextAreaWidget(this);
		}

		if (Number.class.isAssignableFrom(getField().getType()) || getField().getType().equals(double.class)
				|| getField().getType().equals(int.class) || getField().getType().equals(long.class)) {
			widget = new NumberWidget(this);
		}

		if (Boolean.class.isAssignableFrom(getField().getType()) || getField().getType().equals(boolean.class)) {
			widget = new CheckboxWidget(this);
		}

		if (Date.class.isAssignableFrom(getField().getType())) {
			widget = new DateWidget(this);
		}

		if (DateTime.class.isAssignableFrom(getField().getType())) {
			widget = new DateWidget(this);
		}

		if (File.class.isAssignableFrom(getField().getType())) {
			widget = new FileWidget(this);
		}

		if (getField().getType().isEnum()) {
			widget = new SelectWidget(this);
		}
	}

	private <A extends Annotation> A annotation(Class<A> ann) {
		if (field.isAnnotationPresent(ann)) {
			return field.getAnnotation(ann);
		}
		return null;
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

	public boolean isSortable() {
		return sortable;
	}

	public boolean isRequired() {
		return required;
	}
	public boolean isIgnoreEdit() {
		return ignoreEdit;
	}

	public String getDisplayName() {
		return field.getName();
	}

	public Converter<?> getConverter() {
		return converter;
	}

	public FormFieldWidget getWidget() {
		return widget;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FieldMetadata [").append(field).append(", key=").append(key).append(", required=")
				.append(required).append(", sortable=").append(sortable).append(", ignoreEdit=").append(ignoreEdit)
				.append(", converter=").append(converter)
				.append("]");
		return builder.toString();
	}

	
}
