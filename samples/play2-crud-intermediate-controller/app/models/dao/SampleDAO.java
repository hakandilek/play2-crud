package models.dao;

import play.utils.dao.ebean.EbeanDAO;
import models.Sample;

public class SampleDAO extends EbeanDAO<Long, Sample> {

	public SampleDAO() {
		super(Long.class, Sample.class);
		addListener(new SampleDAOListener());
	}

}
