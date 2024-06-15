package org.research.kadda.labinventory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(schema = "CHEMINFRA", name = "RESERVATION")
public class Reservation implements Serializable {

	private static final long serialVersionUID = -8961353425075088310L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESERVATION_GEN")
	@SequenceGenerator(name="RESERVATION_GEN", sequenceName="CHEMINFRA.RESERVATION_SEQ", allocationSize=1)
    int id;

    @Column(name="INSTRID")
    Integer instrid;

    @Column(name="FROMTIME")
	Date fromTime;
    
    @Column(name="TOTIME")
	Date toTime;
    
    String username;
    
    String remark;
    
    @Column(name="RESOPTID")
	Integer resoptid;

	@Column(name="RATIO")
	Integer ratio;

	public Reservation() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getInstrid() {
		return instrid;
	}

	public void setInstrid(Integer instrid) {
		this.instrid = instrid;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getResoptid() {
		return resoptid;
	}

	public void setResoptid(Integer resoptid) {
		this.resoptid = resoptid;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
}
