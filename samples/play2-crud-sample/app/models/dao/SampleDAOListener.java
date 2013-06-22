package models.dao;

import java.util.Random;

import models.Sample;
import play.utils.dao.DAOListener;

public class SampleDAOListener implements DAOListener<Long, Sample> {

	Random random = new Random();
	

	@Override
	public void beforeCreate(Sample m) {
		m.setRandomValue(random.nextInt());
	}

	@Override
	public void beforeUpdate(Sample m) {
		m.setRandomValue(random.nextInt());
	}

	@Override
	public void afterCreate(Long key, Sample m) {
	}

	@Override
	public void afterRemove(Long key, Sample m) {
	}

	@Override
	public void afterUpdate(Sample m) {
	}

	@Override
	public void beforeRemove(Long key) {
	}

}
