package play.utils.crud;

import java.util.List;

public class RestResult {

	public static enum Status {
		OK,

		NOT_FOUND,

		MISSING,

		CANNOT_DELETE,

		;
	}

	Status status;

	Long key;

	String message;

	List<?> list;

	public RestResult(Status status, long key) {
		this.status = status;
		this.key = key;
	}

	public RestResult(Status status, long key, String message) {
		super();
		this.status = status;
		this.key = key;
		this.message = message;
	}

	public RestResult() {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RestResult [status=").append(status).append(", key=").append(key).append("]");
		return builder.toString();
	}

}
