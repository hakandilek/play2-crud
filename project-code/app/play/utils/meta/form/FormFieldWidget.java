package play.utils.meta.form;

import play.api.i18n.Lang;
import play.data.Form;
import play.data.Form.Field;
import play.twirl.api.Html;
import play.utils.meta.FieldMetadata;
import scala.Symbol;
import scala.Tuple2;

import java.util.Locale;

public abstract class FormFieldWidget {

	protected FieldMetadata fieldMetadata;
	private String fieldName;

	public FormFieldWidget(FieldMetadata fieldMetadata) {
		super();
		this.fieldMetadata = fieldMetadata;
		fieldName = fieldMetadata.getField().getName();
	}

	public java.lang.reflect.Field field() {
		return fieldMetadata.getField();
	}

	public Html render(Form<?> form) {
		Field formField = form.field(fieldName);

		Lang lang = getLang();
		@SuppressWarnings("unchecked")
		Tuple2<Symbol, Object>[] args = new Tuple2[] {};
		return render(formField, args, lang);
	}

	protected abstract Html render(Field formField, Tuple2<Symbol, Object>[] args, Lang lang);

	protected Lang getLang() {
		Lang lang = null;
		if (play.mvc.Http.Context.current.get() != null) {
			lang = play.mvc.Http.Context.current().lang();
		} else {
			Locale defaultLocale = Locale.getDefault();
			lang = new Lang(defaultLocale.getLanguage(), defaultLocale.getCountry());
		}
		return lang;
	}
}
