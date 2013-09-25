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

public class ClasspathScanningConverterRegistry implements ConverterRegistry {

	private static ALogger log = Logger.of(ClasspathScanningConverterRegistry.class);

	private Map<Class<?>, Converter<?>> converters;

	public ClasspathScanningConverterRegistry(Application app) {
		this.converters = scan(app.classloader());
	}

	@SuppressWarnings("rawtypes")
	private Map<Class<?>, Converter<?>> scan(ClassLoader classloader) {
		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloader)).setScanners(new SubTypesScanner(false)));

		Map<Class<?>, Converter<?>> map = Maps.newHashMap();
		Set<Class<? extends Converter>> converterClasses = reflections.getSubTypesOf(Converter.class);
		if (log.isDebugEnabled())
			log.debug("converterClasses : " + converterClasses);

		for (Class<? extends Converter> converterClass : converterClasses) {
			try {
				if (log.isDebugEnabled())
					log.debug("converterClass : " + converterClass);

				Converter converter = converterClass.newInstance();
				if (converter != null) {
					Class<?> keyClass = converter.typeClass();
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
	public <K> Converter<K> getConverter(Class<K> type) {
		return (Converter<K>) converters.get(type);
	}

}
