package play.utils.crud;

import play.api.mvc.Action;
import play.api.mvc.AnyContent;

public class CrudAssets {

	public static Action<AnyContent> at(String path, String file) {
		return controllers.Assets.at(path, file);
	}
}
