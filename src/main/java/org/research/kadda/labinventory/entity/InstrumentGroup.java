package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "CHEMINFRA", name = "INSTRUMENTGROUP")
@SequenceGenerator(name="INSTR_SEQ")
public class InstrumentGroup implements Serializable {
	
	private static final long serialVersionUID = 6612740926095862548L;

	@Id
    int id;

    @Column
    String name;
    
    public InstrumentGroup() {
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
}
