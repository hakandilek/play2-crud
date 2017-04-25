package models.dao;

import play.utils.dao.BasicDAO;
import models.Simple;

public class SimpleDAO extends BasicDAO<Long, Simple> {

	public SimpleDAO() {
		super(Long.class, Simple.class);
	}

}
