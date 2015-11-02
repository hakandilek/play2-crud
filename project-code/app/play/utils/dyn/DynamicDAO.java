package play.utils.dyn;

import play.utils.dao.ebean.EbeanDAO;
import play.utils.meta.ModelMetadata;

@SuppressWarnings("rawtypes")
public class DynamicDAO extends EbeanDAO {

	@SuppressWarnings("unchecked")
	public DynamicDAO(ModelMetadata model) {
		super(model.getKeyField().getType(), model.getType());
	}

}
