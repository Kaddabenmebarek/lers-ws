package org.research.kadda.labinventory.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "CHEMINFRA", name = "RESOPTIONS")
public class Resoption implements Serializable {

	private static final long serialVersionUID = 2711469074560994531L;

	@Id
    int id;

    String name;

    String color;

    public Resoption() {
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
    
}
