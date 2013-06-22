package models.dao;

import play.utils.dao.BasicDAO;
import models.Sample;

public class SampleDAO extends BasicDAO<Long, Sample> {

	public SampleDAO() {
		super(Long.class, Sample.class);
		addListener(new SampleDAOListener());
	}

}
