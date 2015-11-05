package models.dao;

import models.Sample;
import play.utils.dao.jpa.JpaDAO;

public class SampleDAO extends JpaDAO<Long, Sample> {

	public SampleDAO() {
		super(Long.class, Sample.class);
		addListener(new SampleDAOListener());
	}

}
