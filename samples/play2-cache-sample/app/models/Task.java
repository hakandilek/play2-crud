package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Page;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.cache.CachedFinder;

@Entity
@SuppressWarnings("serial")
public class Task extends Model {

	public static final int PAGE_SIZE = 10;

	@Id
	private Long key;

	@Basic
	private String title;

	@Required
	private String owner;

	@Basic
	private Date dueDate;

	public static CachedFinder<Long, Task> find = new CachedFinder<Long, Task>(
			Long.class, Task.class);

	/**
	 * @return all tasks from the cache
	 */
	public static List<Task> all() {
		return find.all();
	}

	/**
	 * @param page
	 *            the page number
	 * @return page of all tasks from the cache
	 */
	public static Page<Task> page(int page) {
		return find.page(page, PAGE_SIZE, "dueDate desc");
	}

	/**
	 * @param page
	 *            the page number
	 * @param owner
	 *            results will be filtered (exact match) for this owner value
	 * @return page of all tasks from the cache with the specified owner
	 */
	public static Page<Task> page(int page, String owner) {
		return find.page(page, PAGE_SIZE, "dueDate desc", "owner", owner);
	}

	public static void create(Task task) {
		task.save();
		find.put(task.getKey(), task);
	}

	public static void remove(Long key) {
		find.ref(key).delete();
		find.clean(key);
	}

	public static Task get(Long key) {
		return find.byId(key);
	}

	public static void update(Long key, Task task) {
		task.update(key);
		find.put(task.getKey(), task);
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [key=").append(key).append(", title=")
				.append(title).append(", owner=").append(owner)
				.append(", dueDate=").append(dueDate).append("]");
		return builder.toString();
	}

}
