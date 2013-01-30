package play.utils.crud;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
	private List<Class<?>> types = new ArrayList<Class<?>>();
	private List<Object> values = new ArrayList<Object>();

	public Parameters() {
		super();
	}

	public <T> Parameters(Class<?> type, T value) {
		and(type, value);
	}

	public <T> Parameters and(Class<?> type, T value) {
		types.add(type);
		values.add(value);
		return this;
	}

	public Class<?>[] types() {
		Class<?>[] a = new Class<?>[types.size()];
		types.toArray(a);
		return a;
	}

	public Object[] values() {
		Object[] a = values.toArray();
		return a;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < types.size(); i++) {
			Class<?> type = types.get(i);
			Object value = values.get(i);
			if (i != 0) sb.append(", ");
			sb.append(type.getSimpleName()).append(": ").append(value);
		}
		sb.append(")");
		return sb.toString();
	}
	
	
}
