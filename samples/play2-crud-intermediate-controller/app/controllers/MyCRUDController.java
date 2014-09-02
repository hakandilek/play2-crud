package controllers;

import play.Logger;
import play.data.Form;
import play.utils.crud.CRUDController;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;

public abstract class MyCRUDController<K, M extends BasicModel<K>> extends CRUDController<K, M> {

	private String className;

	public MyCRUDController(DAO<K, M> dao,Class<K> keyClass,
			Class<M> modelClass) {
        super(dao, Form.form(modelClass), keyClass,
                modelClass, 10, "id" + modelClass.getSimpleName());
        className = modelClass.getSimpleName();
        Logger.debug("className: " + className);
	}

}
