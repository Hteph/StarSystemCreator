package com.github.hteph.ObjectsOfAllSorts;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class StellarObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;

	private String description;

	private String archiveID;

	//Constructor -------------------------------------------------------------
	public StellarObject(String name, String description, String archiveID) {

		this.name = name;
		this.description = description;
		this.archiveID = archiveID;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;		
	}

	public abstract BigDecimal getOrbitalDistance();

	public String getArchiveID(){return archiveID;}

	public void setArchiveID(String archiveID) {
		this.archiveID = archiveID;
	}
}
