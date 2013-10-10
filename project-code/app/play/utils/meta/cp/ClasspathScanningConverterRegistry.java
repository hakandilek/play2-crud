package play.utils.meta.cp;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import play.Logger;
import play.Logger.ALogger;
import play.utils.meta.ConverterRegistry;
import play.utils.meta.convert.Converter;

import com.google.common.collect.Maps;

public class ClasspathScanningConverterRegistry implements ConverterRegistry {

	private static ALogger log = Logger.of(ClasspathScanningConverterRegistry.class);

	private Map<Class<?>, Converter<?>> converters;

	public ClasspathScanningConverterRegistry(ClassLoader... cls) {
		this.converters = scan(cls);
	}

	@SuppressWarnings("rawtypes")
	private Map<Class<?>, Converter<?>> scan(ClassLoader... classloaders) {
		if (log.isDebugEnabled())
			log.debug("scan <-");
		
		Collection<URL> urls = ClasspathHelper.forPackage("play.utils.meta.convert", classloaders);
		if (log.isDebugEnabled())
			log.debug("urls : " + urls);

		Configuration configs = new ConfigurationBuilder().setUrls(
				urls).addClassLoaders(classloaders).setScanners(new SubTypesScanner(false));
		
		final Reflections reflections = new Reflections(configs);

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
