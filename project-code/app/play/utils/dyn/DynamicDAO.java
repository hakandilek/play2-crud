package play.utils.dyn;

import play.utils.dao.BasicDAO;
import play.utils.meta.ModelMetadata;

@SuppressWarnings("rawtypes")
public class DynamicDAO extends BasicDAO {

	@SuppressWarnings("unchecked")
	public DynamicDAO(ModelMetadata model) {
		super(model.getKeyField().getType(), model.getType());
	}

}
