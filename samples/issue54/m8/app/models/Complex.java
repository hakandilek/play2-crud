package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.annotation.EnumValue;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

@Entity
@SuppressWarnings("serial")
public class Complex extends Model implements BasicModel<Long> {

	@Id
	private Long key;

	@Basic
	@Required
	private String stringField;

	@Basic
	@Required
	@MaxLength(256)
	@Column(length = 256)
	private String longStringField;

	@Basic
	@Required
	private Integer integerField;

	@Basic
	@Required
	private Double doubleField;

	@Basic
	@Required
	private Boolean booleanField;

	@Basic
	@Required
	private Date dateField;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public Double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	public Boolean getBooleanField() {
		return booleanField;
	}

	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}

	public String getLongStringField() {
		return longStringField;
	}

	public void setLongStringField(String longStringField) {
		this.longStringField = longStringField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Complex [key=").append(key).append(", stringField=").append(stringField)
				.append(", integerField=").append(integerField).append(", doubleField=").append(doubleField)
				.append(", booleanField=").append(booleanField).append("]");
		return builder.toString();
	}

	static enum Select {
		@EnumValue("1")
		OPTION1,

		@EnumValue("2")
		OPTION2,

		@EnumValue("3")
		OPTION3,

		;
	}
}
