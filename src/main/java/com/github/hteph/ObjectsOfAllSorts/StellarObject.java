package com.github.hteph.ObjectsOfAllSorts;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class StellarObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String description;
	protected String archiveID;

	//Constructor -------------------------------------------------------------

	public StellarObject(String name, String description, String archiveID) {
		super();
		this.name = name;
		this.description = description;
		this.archiveID = archiveID;
	}

	public String getName() {
	
		return name;
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
