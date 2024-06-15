package org.research.kadda.labinventory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "CHEMINFRA", name = "FAVORITE_INSTRUMENT")
@SequenceGenerator(name = "FAVORITE_GEN", sequenceName = "CHEMINFRA.FAV_SEQ", allocationSize = 1)
public class Favorite implements Serializable {

	private static final long serialVersionUID = 7438000922125259994L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAVORITE_GEN")
	int id;

	@Column
	String userName;

	@Column(name = "CRE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	Date creationDate;

	@Column
	int isActive;

	/*@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
	@JoinColumn(name = "INSTRID", nullable = false)*/
	@Column(name = "INSTRUMENT_ID")
	Integer instrid;

	public Favorite() {
		super();
	}

	public Favorite(String userName, Date creationDate, int isActive, Integer instrid) {
		super();
		this.userName = userName;
		this.creationDate = creationDate;
		this.isActive = isActive;
		this.instrid = instrid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Integer getInstrid() {
		return instrid;
	}

	public void setInstrid(Integer instrid) {
		this.instrid = instrid;
	}

}
