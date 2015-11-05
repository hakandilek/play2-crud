package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Result;
import play.utils.crud.APIController;

public class SampleRestController extends APIController<Long, Sample> {

	@Inject
	public SampleRestController(SampleDAO dao) {
		super(dao, Long.class, Sample.class);
	}

	@Override
	public Result create() {
		Result check = checkRequired("name");
		if (check != null) {
			if (log.isDebugEnabled())
				log.debug("check : " + check);
			return check;
		}

		String name = jsonText("name");

		Sample m = new Sample();
		m.setName(name);
		
		Long key = dao.create(m);
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		return created(toJson(ImmutableMap.of("status", "OK", "key", key)));
	}

}
