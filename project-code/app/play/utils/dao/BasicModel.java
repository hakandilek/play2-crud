package play.utils.dao;

public interface BasicModel<K> {

	K getKey();
	void save();
	void delete();
	void update(Object key);
	void update();
	void saveManyToManyAssociations(String association);
	
}
