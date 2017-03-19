package play.utils.meta.form;

import java.util.Map;
import java.util.TreeMap;

import play.api.i18n.Lang;
import play.twirl.api.Html;
import play.data.Form.Field;
import play.utils.meta.FieldMetadata;
import scala.Symbol;
import scala.Tuple2;
import views.html.helper.FieldConstructor;

public class SelectWidget extends FormFieldWidget {

	private Map<Object, String> options;

	public SelectWidget(FieldMetadata fieldMetadata) {
		super(fieldMetadata);
		Class<?> declaringClass = field().getDeclaringClass();
		String parentClassName = declaringClass.getSimpleName();
		Object[] constants=fieldMetadata.getType().getEnumConstants();
		options = new TreeMap<Object, String>();
		for (Object constant : constants) {
			options.put(constant, parentClassName + "." + constant);
		}
	}

	@Override
	protected Html render(Field formField, Tuple2<Symbol, Object>[] args, Lang lang) {
		return play.utils.meta.form.html.selectWidget.render(formField, options, args, lang);
	}

}
