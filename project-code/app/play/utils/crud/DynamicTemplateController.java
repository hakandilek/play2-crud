package play.utils.crud;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;

public class DynamicTemplateController extends Controller {


	protected final ALogger log = Logger.of(getClass());

	protected Result ok(String template, Parameters params) {
		Content content;
		try {
			content = call("views.html." + template, "render", params);
		} catch (ClassNotFoundException e) {
			return internalServerError(templateNotFound(template, params));
		}
		return ok(content);
	}

	protected Result badRequest(String template, Parameters params) {
		Content content;
		try {
			content = call("views.html." + template, "render", params);
		} catch (ClassNotFoundException e) {
			return internalServerError(templateNotFound(template, params));
		}
		return badRequest(content);
	}
	
	private String templateNotFound(String template, Parameters params) {
		StringBuilder sb = new StringBuilder("Template ");
		sb.append(template).append("(");
		Class<?>[] types = params.types();
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (i != 0) sb.append(", ");
			sb.append(type.getSimpleName());
		}
		sb.append(") is not found");
		return sb.toString();
	}

	protected <T> Parameters with() {
		return new Parameters();
	}
	
	protected <T> Parameters with(Class<?> paramType, T paramValue) {
		return new Parameters(paramType, paramValue);
	}

	@SuppressWarnings("unchecked")
	private <R> R call(String className, String methodName, Parameters params) throws ClassNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
		Object result = null;
		Class<?> clazz = classLoader.loadClass(className);
		Class<?>[] paramTypes = params.types();
		Object[] paramValues = params.values();
		Method method = ReflectionUtils.findMethod(clazz, methodName,
				paramTypes);
		result = ReflectionUtils.invokeMethod(method, null, paramValues);
		return (R) result;
	}

}
