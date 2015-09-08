package play.utils.meta.convert;

import java.util.UUID;

/**
 * Created by wilton risenhoover on 9/7/15.
 */
public class UUIDConverter extends Converter<java.util.UUID> {

    public UUIDConverter() {
        super(java.util.UUID.class);
    }

    @Override
    public java.util.UUID convert(final String key) {
        return UUID.fromString(key);
    }

}
