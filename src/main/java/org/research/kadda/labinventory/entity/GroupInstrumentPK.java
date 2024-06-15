package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupInstrumentPK implements Serializable {

	private static final long serialVersionUID = 8072822756966099293L;

	@Column(name = "IID")
	private int instId;

	@Column(name = "GID")
	private int gId;

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

}
