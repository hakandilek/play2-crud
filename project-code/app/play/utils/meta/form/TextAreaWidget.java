package play.utils.meta.form;

import play.api.i18n.Lang;
import play.twirl.api.Html;
import play.data.Form.Field;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.html.textareaWidget;
import scala.Symbol;
import scala.Tuple2;
import views.html.helper.FieldConstructor;

public class TextAreaWidget extends FormFieldWidget {

	public TextAreaWidget(FieldMetadata fieldMetadata) {
		super(fieldMetadata);
	}
	
	@Override
	protected Html render(Field formField, Tuple2<Symbol, Object>[] args, FieldConstructor fieldConstructor, Lang lang) {
		return textareaWidget.render(formField, args, fieldConstructor, lang);
	}


}
