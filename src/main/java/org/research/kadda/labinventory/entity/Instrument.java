package org.research.kadda.labinventory.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Kadda
 */

@Entity
@Table(schema = "CHEMINFRA", name = "INSTRUMENT")
@SequenceGenerator(name = "INSTR_SEQ", sequenceName = "CHEMINFRA.INSTRUMENT_SEQ", allocationSize = 1)
public class Instrument implements Serializable {

	private static final long serialVersionUID = -6768728803757647951L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSTR_SEQ")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "DESCRIPTION")
	private String description = "";

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "RESERVABLE")
	private int reservable = 1;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "GROUPNAME")
	private String groupname;

	@Column(name = "INFOTITLE")
	private String infotitle = "";

	@Column(name = "INFOMESSAGE")
	private String infomessage = "";

	@Column(name = "ISPUBLIC")
	private int ispublic = 0;

	@Column(name = "RATIOCOMMENT")
	private String ratioComment;

	@Column
	private int selectoverlap = 0;

	@Column(name = "EMAILNOTIFICATION")
	private int emailNotification;
	
	@Column(name = "STEP_INCREMENT")
	private int stepIncrement;

	@Column(name = "START_TIMEPOINT")
	private String startTimepoint;
	
	@Column(name = "MAX_DAYS")
	private Integer maxDays;
	
	@Column(name = "HIGHLIGHT_COMMENT")
	private int highlightComment = 0;
	
	@Transient
	private int favorite;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getReservable() {
		return reservable;
	}

	public void setReservable(int reservable) {
		this.reservable = reservable;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupName) {
		this.groupname = groupName;
	}

	public String getInfoTitle() {
		return infotitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infotitle = infoTitle;
	}

	public String getInfoMessage() {
		return infomessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infomessage = infoMessage;
	}

	public int getIsPublic() {
		return ispublic;
	}

	public void setIsPublic(int isPublic) {
		this.ispublic = isPublic;
	}

	public int getSelectOverlap() {
		return selectoverlap;
	}

	public void setSelectOverlap(int selectoverlap) {
		this.selectoverlap = selectoverlap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public String getRatioComment() {
		return ratioComment;
	}

	public void setRatioComment(String ratioComment) {
		this.ratioComment = ratioComment;
	}

	public int getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}

	public int getStepIncrement() {
		return stepIncrement;
	}

	public void setStepIncrement(int stepIncrement) {
		this.stepIncrement = stepIncrement;
	}

	public String getInfotitle() {
		return infotitle;
	}

	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}

	public String getInfomessage() {
		return infomessage;
	}

	public void setInfomessage(String infomessage) {
		this.infomessage = infomessage;
	}

	public int getIspublic() {
		return ispublic;
	}

	public void setIspublic(int ispublic) {
		this.ispublic = ispublic;
	}

	public int getSelectoverlap() {
		return selectoverlap;
	}

	public void setSelectoverlap(int selectoverlap) {
		this.selectoverlap = selectoverlap;
	}

	public String getStartTimepoint() {
		return startTimepoint;
	}

	public void setStartTimepoint(String startTimepoint) {
		this.startTimepoint = startTimepoint;
	}

	public Integer getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(Integer maxDays) {
		this.maxDays = maxDays;
	}

	public int getHighlightComment() {
		return highlightComment;
	}

	public void setHighlightComment(int highlightComment) {
		this.highlightComment = highlightComment;
	}

}
