package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "CHEMINFRA", name = "RESOPTIONSLINK")
public class ResOptionLink implements Serializable {

	private static final long serialVersionUID = 3252042959202170540L;

	@EmbeddedId
	ResOptionLinkPK resOptionLinkpk;

	public ResOptionLinkPK getResOptionLinkpk() {
		return resOptionLinkpk;
	}

	public void setPk(ResOptionLinkPK resOptionLinkpk) {
		this.resOptionLinkpk = resOptionLinkpk;
	}

}
