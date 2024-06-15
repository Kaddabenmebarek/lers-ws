package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "CHEMINFRA", name = "INSTRUMENT_PRIORITY_USERS")
@SequenceGenerator(name="INSTRPRIORUSR_SEQ", sequenceName="CHEMINFRA.INSTRPRIORUSR_SEQ", allocationSize=1)
public class InstrumentPriorityUsers implements Serializable {

	private static final long serialVersionUID = -5802208052350030395L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTRPRIORUSR_SEQ")
	int id;

	@Column
	int instrumentId;

	@Column
	String priorityUser;

	public InstrumentPriorityUsers() {
	}

	public InstrumentPriorityUsers(int id, int instrumentId, String priorityUser) {
		super();
		this.id = id;
		this.instrumentId = instrumentId;
		this.priorityUser = priorityUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getPriorityUser() {
		return priorityUser;
	}

	public void setPriorityUser(String priorityUser) {
		this.priorityUser = priorityUser;
	}


}
