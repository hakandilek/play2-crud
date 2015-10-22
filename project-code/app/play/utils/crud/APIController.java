package play.utils.crud;

import static play.libs.Json.toJson;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.jpa.repository.JpaRepository;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.common.collect.ImmutableMap;

public abstract class APIController<M, K extends Serializable> extends Controller implements CRUD<M, K> {

	protected final ALogger log = Logger.of(getClass());

	protected final JpaRepository<M, K> repo;

	protected final Class<K> keyClass;

	protected final Class<M> modelClass;

	@Inject
	public APIController(JpaRepository<M, K> repo, Class<K> keyClass, Class<M> modelClass) {
		super();
		this.repo = repo;
		this.keyClass = keyClass;
		this.modelClass = modelClass;
	}

	public abstract Result create();

	
	public JpaRepository<M, K> getRepo() {
		return repo;
	}

	public Class<K> getKeyClass() {
		return keyClass;
	}

	public Class<M> getModelClass() {
		return modelClass;
	}

	public Result update(K key) {
		if (log.isDebugEnabled())
			log.debug("update <- " + key);

		Result check = checkRequired("name", "value");
		if (check != null) {
			return check;
		}

		// field name & value
		String name = jsonText("name");
		String value = jsonText("value");
		if (log.isDebugEnabled())
			log.debug("name : " + name);
		if (log.isDebugEnabled())
			log.debug("value : " + value);

		M m = repo.findOne(key);
		if (log.isDebugEnabled())
			log.debug("m : " + m);
		if (m == null) {
			return notFound(toJson(ImmutableMap.of("status", "NOT_FOUND", "key", key, "message",
					"entity with the given key not found")));
		}

		String fieldName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		try {
			setField(m, fieldName, value);
		} catch (NoSuchMethodException e) {
			return notFound(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "field[" + name
					+ "] on entity not found")));
		} catch (SecurityException e) {
			return internalServerError(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "error occured : "
					+ e)));
		} catch (IllegalAccessException e) {
			return internalServerError(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "error occured : "
					+ e)));
		} catch (IllegalArgumentException e) {
			return internalServerError(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "error occured : "
					+ e)));
		} catch (InvocationTargetException e) {
			return internalServerError(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "error occured : "
					+ e)));
		}

		repo.save(m);
		if (log.isDebugEnabled())
			log.debug("updated.");
		if (log.isDebugEnabled())
			log.debug("m : " + m);

		return ok(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "field[" + name + "] updated")));
	}

	protected <S> void setField(S s, String fieldName, String value) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> cls = s.getClass();
		Method method = cls.getMethod("set" + fieldName, String.class);
		method.invoke(s, value);
	}

	public Result list() {
		List<M> list = repo.findAll();
		return ok(toJson(ImmutableMap.of("status", "OK", "list", list)));
	}

	public Result read(K key) {
		M model = repo.findOne(key);
		if (model == null) {
			return notFound(toJson(ImmutableMap.of("status", "NOT_FOUND", "key", key)));
		}
		return ok(toJson(ImmutableMap.of("status", "OK", "key", key, "data", model)));
	}

	public Result delete(K key) {
		try {
			repo.delete(key);
		} catch (OptimisticLockException e) {
			return notFound(toJson(ImmutableMap.of("status", "CANNOT_DELETE", "key", key)));
		} catch (Exception e) {
			return notFound(toJson(ImmutableMap.of("status", "NOT_FOUND", "key", key)));
		}
		return ok(toJson(ImmutableMap.of("status", "OK", "key", key, "message", "deleted :" + key)));
	}

	protected String jsonText(String name) {
		JsonNode json = request().body().asJson();
		JsonNode node = json.get(name);
		if (node == null)
			return null;
		String value = node.asText();
		return value;
	}

	protected List<String> jsonTextList(String name) {
		JsonNode json = request().body().asJson();
		JsonNode node = json.get(name);
		if (node == null)
			return null;
		List<String> list = new ArrayList<String>();
		for (Iterator<JsonNode> elems = node.elements(); elems.hasNext();) {
			JsonNode elem = elems.next();
			list.add(elem.asText());
		}
		return list;
	}

	protected Result checkRequired(String... params) {
		List<String> missing = new ArrayList<String>();
		for (String param : params) {
			String val = jsonText(param);
			if (val == null) {
				missing.add(param);
			}
		}
		if (!missing.isEmpty()) {
			return badRequest(toJson(ImmutableMap.of("status", "MISSING", "message",
					"Missing parameters :" + missing.toString())));
		}
		return null;
	}

	public static Result invalid(String res) {
		String method = request().method();
		String resource = request().path();
		return notFound(toJson(ImmutableMap.of("status", "NOT_FOUND", "message", "resource not found:" + method + " "
				+ resource)));
	}

}
