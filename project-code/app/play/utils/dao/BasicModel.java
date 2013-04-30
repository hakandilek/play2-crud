package play.utils.dao;

public interface BasicModel<K> {

	K getKey();

	void setKey(K key);

	void save();

	void delete();

	void update();

	void saveManyToManyAssociations(String association);

}
