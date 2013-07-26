package play.utils.meta;

import java.util.Collection;

public interface ModelRegistry {

	Collection<ModelMetadata> getModels();
	
	<M> ModelMetadata getModel(Class<M> modelType);

}
