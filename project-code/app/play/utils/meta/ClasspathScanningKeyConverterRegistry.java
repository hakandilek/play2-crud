package play.utils.meta;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Maps;

import play.Application;
import play.Logger;
import play.Logger.ALogger;

public class ClasspathScanningKeyConverterRegistry implements KeyConverterRegistry {

	private static ALogger log = Logger.of(ClasspathScanningKeyConverterRegistry.class);

	private Map<Class<?>, KeyConverter<?>> converters;

	public ClasspathScanningKeyConverterRegistry(Application app) {
		this.converters = scan(app.classloader());
	}

	@SuppressWarnings("rawtypes")
	private Map<Class<?>, KeyConverter<?>> scan(ClassLoader classloader) {
		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloader)).setScanners(new SubTypesScanner(false)));

		Map<Class<?>, KeyConverter<?>> map = Maps.newHashMap();
		Set<Class<? extends KeyConverter>> converterClasses = reflections.getSubTypesOf(KeyConverter.class);
		if (log.isDebugEnabled())
			log.debug("converterClasses : " + converterClasses);

		for (Class<? extends KeyConverter> converterClass : converterClasses) {
			try {
				if (log.isDebugEnabled())
					log.debug("converterClass : " + converterClass);

				KeyConverter converter = converterClass.newInstance();
				if (converter != null) {
					Class<?> keyClass = converter.keyClass();
					log.info("Converter:" + keyClass + " : " + converter);
					map.put(keyClass, converter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> KeyConverter<K> getConverter(Class<K> type) {
		return (KeyConverter<K>) converters.get(type);
	}

}
