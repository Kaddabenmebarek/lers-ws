package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "CHEMINFRA", name = "GROUPINSTRUMENT")
public class GroupInstrument implements Serializable {

	private static final long serialVersionUID = 4556476910873910476L;
	
	@EmbeddedId
	GroupInstrumentPK groupInstrumentpk;

	public GroupInstrumentPK getGroupInstrumentpk() {
		return groupInstrumentpk;
	}

	public void setGroupInstrumentpk(GroupInstrumentPK groupInstrumentpk) {
		this.groupInstrumentpk = groupInstrumentpk;
	}

}
