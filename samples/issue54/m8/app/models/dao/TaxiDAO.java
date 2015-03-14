package models.dao;

import play.utils.dao.BasicDAO;
import models.Taxi;

public class TaxiDAO extends BasicDAO<Long, Taxi> {

	public TaxiDAO() {
		super(Long.class, Taxi.class);
		//addListener(new TaxiDAOListener());
	}

}
