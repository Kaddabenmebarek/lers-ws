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
@Table(schema = "CHEMINFRA", name = "INSTRUMENT_EMPLOYEE_GROUP")
@SequenceGenerator(name="INSTRUMENT_EMPLOYEE_GROUP_GEN", sequenceName="CHEMINFRA.INSTRUMENT_EMPLOYEE_GROUP_SEQ", allocationSize=1)
public class InstrumentEmployeeGroup implements Serializable {

	private static final long serialVersionUID = 1435500577331083024L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTRUMENT_EMPLOYEE_GROUP_GEN")
	int id;

	@Column(name="INSTRUMENT_ID")
	int instrumentId;

	@Column(name="EMPLOYEE_GROUP_NAME")
	String groupName;

	public InstrumentEmployeeGroup() {
	}

	public InstrumentEmployeeGroup(int id, int instrumentId, String groupName) {
		super();
		this.id = id;
		this.instrumentId = instrumentId;
		this.groupName = groupName;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


}
