package play.utils.dao;

import java.util.Date;

public interface TimestampModel<K> extends ActiveObjectModel<K> {

	Date getCreatedOn();

	void setCreatedOn(Date createdOn);

	Date getUpdatedOn();

	void setUpdatedOn(Date updatedOn);

	int getRevision();

	void setRevision(int revision);

}
