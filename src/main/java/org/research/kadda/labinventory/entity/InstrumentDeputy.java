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
@Table(schema = "CHEMINFRA", name = "INSTRUMENT_DEPUTY")
@SequenceGenerator(name="INSTRDEPUTY_SEQ", sequenceName="CHEMINFRA.INSTRDEPUTY_SEQ", allocationSize=1)
public class InstrumentDeputy implements Serializable {

	private static final long serialVersionUID = -5802208052350030395L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTRDEPUTY_SEQ")
	int id;

	@Column
	int instrumentId;

	@Column
	String deputy;

	public InstrumentDeputy() {
	}

	public InstrumentDeputy(int id, int instrumentId, String deputy) {
		super();
		this.id = id;
		this.instrumentId = instrumentId;
		this.deputy = deputy;
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

	public String getDeputy() {
		return deputy;
	}

	public void setDeputy(String deputy) {
		this.deputy = deputy;
	}

}
