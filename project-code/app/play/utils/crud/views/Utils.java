package play.utils.crud.views;

import java.lang.reflect.Field;

import play.Logger;
import play.Logger.ALogger;
import play.api.templates.Html;
import play.i18n.Messages;
import play.utils.meta.FieldMetadata;
import play.utils.meta.ModelMetadata;

public class Utils {

	private static ALogger log = Logger.of(Utils.class);

	public static String appName() {
		return Messages.get("crud.appname");
	}

	public static Html listRowField(FieldMetadata fieldMetadata, Object o) {
		return Html.apply(valueStr(fieldMetadata, o));
	}

	public static Html showField(FieldMetadata fieldMetadata, Object o) {
		return Html.apply(valueStr(fieldMetadata, o));
	}

	public static String keyValue(FieldMetadata fieldMetadata, Object o) {
		return valueStr(fieldMetadata, o);
	}

	public static String keyValue(ModelMetadata model, Object o) {
		FieldMetadata keyField = model.getKeyField();
		return valueStr(keyField, o);
	}

	public static String valueStr(FieldMetadata fieldMetadata, Object o) {
		Object value = value(fieldMetadata, o);
		if (value != null)
			return value.toString();
		return "N/A";
	}

	public static Object value(FieldMetadata fieldMetadata, Object o) {
		Field field = fieldMetadata.getField();
		try {
			field.setAccessible(true);
			Object resultObj = field.get(o);
			return resultObj;
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("exception while accessing field value: " + field, e);
			return null;
		}
	}
}
