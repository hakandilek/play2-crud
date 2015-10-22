package play.utils.meta.cp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import play.Logger;
import play.Logger.ALogger;
import play.utils.meta.ConverterRegistry;
import play.utils.meta.FieldMetadata;
import play.utils.meta.ModelMetadata;
import play.utils.meta.ModelRegistry;
import play.utils.meta.convert.Converter;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ClasspathScanningModelRegistry implements ModelRegistry {

	private static ALogger log = Logger.of(ClasspathScanningModelRegistry.class);
	
	private Map<Class<?>, ModelMetadata> models;
	private ConverterRegistry converters;

	public ClasspathScanningModelRegistry(ConverterRegistry converters, ClassLoader... cls) {
		this.converters = converters;
		this.models = scan(cls);
	}

	private Map<Class<?>, ModelMetadata> scan(ClassLoader... classloaders) {
		Map<Class<?>, ModelMetadata> map = Maps.newHashMap();

		final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("", classloaders)).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner()).addClassLoaders(classloaders));

		Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);

		final Reflections domainReflections = new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage("com.teksourcery")).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner()).addClassLoaders(classloaders));

		entities.addAll(domainReflections.getTypesAnnotatedWith(Entity.class));

		for (Class<?> entity : entities) {
			ModelMetadata metadata = getMetadata(entity);
			map.put(entity, metadata);
		}

		return map;
	}

	private ModelMetadata getMetadata(Class<?> entity) {
		if (log.isDebugEnabled())
			log.debug("getMetadata for: " + entity);
		List<Field> modelFields = Lists.newArrayList(entity.getDeclaredFields());

		Class<?> superClass = entity.getSuperclass();
		while (superClass != null
				/*&& Iterables.tryFind(Lists.newArrayList(superClass.getAnnotations()), new Predicate<Annotation>() {
					@Override
					public boolean apply(Annotation annotation) {
						return annotation.annotationType().equals(MappedSuperclass.class);
					}
				}).isPresent()*/) {

			modelFields.addAll(Lists.newArrayList(superClass.getDeclaredFields()));
			superClass = superClass.getSuperclass();
		}
		if (log.isDebugEnabled())
			log.debug("superClass : " + superClass);

		Function<Field, FieldMetadata> fieldMetadata = extractFieldMetadata();
		Function<FieldMetadata, String> fieldName = extractFieldName();
		@SuppressWarnings("unchecked")
		Map<String, FieldMetadata> fields = Maps.uniqueIndex(
				Iterables.transform(
						Iterables.filter(modelFields, Predicates.<Field> and(
								Predicates.not(ReflectionUtils.withAnnotation(Transient.class)),
								Predicates.not(ReflectionUtils.withModifier(Modifier.STATIC)),
								Predicates.not(ReflectionUtils.withModifier(Modifier.FINAL)),
								Predicates.not(new Predicate<Field>() {
									@Override
									public boolean apply(Field field) {
										return field.getName().startsWith("_ebean");
									}
								}))), fieldMetadata), fieldName);
		
		if (log.isDebugEnabled()) {
			log.debug("fields : ");
			Set<String> fieldNames = fields.keySet();
			for (String fn : fieldNames) {
				log.debug(" * " + fn + " : " + fields.get(fn));
			}
		}

		Optional<FieldMetadata> keyField = Iterables.tryFind(fields.values(), new Predicate<FieldMetadata>() {
			@Override
			public boolean apply(FieldMetadata fieldInfo) {
				return fieldInfo.isKey();
			}
		});
		if (log.isDebugEnabled())
			log.debug("keyField : " + keyField);
		
		ModelMetadata metadata = null; 
		if (keyField.isPresent()) {
			metadata = new ModelMetadata(entity, fields, keyField.get());
		}
		return metadata;
	}

	@Override
	public Collection<ModelMetadata> getModels() {
		return models.values();
	}

	@Override
	public <M> ModelMetadata getModel(Class<M> modelType) {
		return models.get(modelType);
	}

	private FieldMetadata toFieldMetadata(Field field) {
		Converter<?> converter = converters.getConverter(field.getType());
		return new FieldMetadata(field, converter);
	}

	private Function<Field, FieldMetadata> extractFieldMetadata() {
		return new Function<Field, FieldMetadata>() {
			@Override
			public FieldMetadata apply(Field field) {
				return toFieldMetadata(field);
			}
		};
	}

	private Function<FieldMetadata, String> extractFieldName() {
		return new Function<FieldMetadata, String>() {
			@Override
			public String apply(FieldMetadata fieldInfo) {
				return fieldInfo.getField().getName();
			}
		};
	}

	public Iterable<? extends String> getModelNames() {
		Collection<ModelMetadata> models = getModels();
		List<String> list = new ArrayList<String>();
		for (ModelMetadata model : models) {
			list.add(model.getName());
		}
		return list ;
	}

}
