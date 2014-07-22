package play.utils.meta.convert;

/**
 *  * Created by pablo on 7/22/14.
 *   */
public class StringConverter extends Converter<String> {

    public StringConverter() {
        super(String.class);
    }

    @Override
          public String convert(String key) {
        return key;
    }

}
