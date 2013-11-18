package play.utils.crud;

import play.api.mvc.Action;
import play.api.mvc.AnyContent;

public class Assets {

	public static Action<AnyContent> at(String path, String file) {
		return controllers.Assets.at(path, file);
	}
	
}
