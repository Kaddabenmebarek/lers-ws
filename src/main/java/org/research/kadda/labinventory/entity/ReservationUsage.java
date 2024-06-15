package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(schema = "CHEMINFRA", name = "RESERVATION_USAGE")
@SequenceGenerator(name="RESERVATIONUSAGE_SEQ", sequenceName="CHEMINFRA.RESERVATION_USAGE_SEQ", allocationSize=1)
public class ReservationUsage implements Serializable {

	private static final long serialVersionUID = -462518253593620207L;

	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESERVATIONUSAGE_SEQ")
	int id;

	@Column(name = "RESERVATION_ID")
	Integer reservationId;

	@Column(name = "PROJECT")
	String project;

	@Column(name = "COMPOUND")
	String compound;

	@Column(name = "BATCH")
	String batch;

	@Column(name = "SAMPLE")
	String sample;

	@Column(name = "SAMPLE_TYPE")
	String sampleType;
	
	@Column(name = "SPECIE")
	String specie;
	
	public ReservationUsage() {
		super();
	}

	public ReservationUsage(int id, Integer reservationId, String project, String compound, String batch,
			String sample, String sampleType, String specie) {
		super();
		this.id = id;
		this.reservationId = reservationId;
		this.project = project;
		this.compound = compound;
		this.batch = batch;
		this.sample = sample;
		this.sampleType = sampleType;
		this.specie = specie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCompound() {
		return compound;
	}

	public void setCompound(String compound) {
		this.compound = compound;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}
	

}
