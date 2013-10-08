package play.utils.meta.form;

import play.api.i18n.Lang;
import play.api.templates.Html;
import play.data.Form.Field;
import play.utils.meta.FieldMetadata;
import scala.Symbol;
import scala.Tuple2;
import views.html.helper.FieldConstructor;

public class NumberWidget extends FormFieldWidget {

	public NumberWidget(FieldMetadata fieldMetadata) {
		super(fieldMetadata);
	}

	@Override
	protected Html render(Field formField, Tuple2<Symbol, Object>[] args, FieldConstructor fieldConstructor, Lang lang) {
		return play.utils.meta.form.html.numberWidget.render(formField, args, fieldConstructor, lang);
	}


}
