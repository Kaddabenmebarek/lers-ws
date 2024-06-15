package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ResOptionLinkPK implements Serializable {

	private static final long serialVersionUID = 5247052155913525355L;

	@Column(name = "INSTID")
	private int instId;

	@Column(name = "OPTID")
	private int optId;

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public int getOptId() {
		return optId;
	}

	public void setOptId(int optId) {
		this.optId = optId;
	}

}
