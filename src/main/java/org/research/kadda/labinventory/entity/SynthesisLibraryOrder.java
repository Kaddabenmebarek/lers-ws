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
@Table(schema = "CHEMINFRA", name = "SYNTHESIS_LIBRARY_ORDER")
@SequenceGenerator(name = "SYNTHESIS_ORDER_GEN", sequenceName = "CHEMINFRA.SYNTHESIS_LIBRARY_ORDER_SEQ", allocationSize = 1)
public class SynthesisLibraryOrder implements Serializable {

	private static final long serialVersionUID = 1317107522027826615L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYNTHESIS_ORDER_GEN")
	private int id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "LINK")
	private String link;

	@Column(name = "PROJECT")
	private String project;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "REQUESTER")
	private String requester;

	@Column(name = "REQUESTTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime;

	@Column(name = "FROMTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromTime;

	@Column(name = "TOTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date toTime;

	@Column(name = "CREATIONTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	@Column(name = "DONETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doneTime;

	@Column(name = "DEPARTMENTNAME")
	private String departmentName;

	@Column(name = "COMPOUND")
	private String compound;

	@Column(name = "QUANTITY")
	private float quantity;

	@Column(name = "UNIT")
	private String unit;

	@Column(name = "UPDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Column(name = "DONE")
	int done = 0;

	@Column(name = "LIBRARYOUTCOME")
	private String libraryOutcome;

	@Column(name = "REMARKS")
	private String remarks;

	public SynthesisLibraryOrder() {
		super();
	}

	public SynthesisLibraryOrder(String title, String link, String project, String username, String requester,
			Date requestTime, Date fromTime, Date toTime, Date creationTime, String departmentName, String compound,
			int quantity, String unit, Date updateTime, int done, String libraryOutcome) {
		super();
		this.title = title;
		this.link = link;
		this.project = project;
		this.username = username;
		this.requester = requester;
		this.requestTime = requestTime;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.creationTime = creationTime;
		this.departmentName = departmentName;
		this.compound = compound;
		this.quantity = quantity;
		this.unit = unit;
		this.updateTime = updateTime;
		this.done = done;
		this.libraryOutcome = libraryOutcome;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public int getId() {
		return id;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCompound() {
		return compound;
	}

	public void setCompound(String compound) {
		this.compound = compound;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLibraryoutcome() {
		return libraryOutcome;
	}

	public void setLibraryoutcome(String libraryOutcome) {
		this.libraryOutcome = libraryOutcome;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
