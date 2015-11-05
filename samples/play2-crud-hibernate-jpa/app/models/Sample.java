package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import play.data.validation.Constraints.Required;
import play.utils.dao.SimpleModel;

@Entity
public class Sample implements SimpleModel<Long> {

	@Id
	@SequenceGenerator(name = "sample_seq", sequenceName = "sample_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sample_seq")
	private Long key;

	@Basic
	@Required
	private String name;
	
	@Basic
	
	@Transient
	private int randomValue;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRandomValue() {
		return randomValue;
	}

	public void setRandomValue(int randomValue) {
		this.randomValue = randomValue;
	}

	@Override
	public String toString() {
		return "Sample [key=" + key + ", name=" + name + "]";
	}

}
