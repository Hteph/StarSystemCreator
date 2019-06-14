package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Star extends StellarObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal lumosity;
	private BigDecimal radius;
	private String classification;
	private BigDecimal age;
	private ArrayList<String> orbitalObjects = new ArrayList<>();
	private int abundance;
	private BigDecimal mass;
	
	// Constructor ------------------------------------------------------
	
	public Star(String archiveID,String name, String description, double lumosity, double mass, double radius, String classification, double age, int abundance) {
		
		super(archiveID, name, description);
		
		this.lumosity = BigDecimal.valueOf(lumosity);
		this.radius = BigDecimal.valueOf(radius);
		this.classification = classification;
		this.age = BigDecimal.valueOf(age);
		this.abundance = abundance;
		this.mass = BigDecimal.valueOf(mass);
		orbitalObjects.add(this.getArchiveID());
		CentralRegistry.putInArchive(this);
	}

	//Getters and Setter

	public BigDecimal getLumosity() {
		return lumosity;
	}

	public BigDecimal getRadius() {
		return radius;
	}

	public String getClassification() {
		return classification;
	}

	public BigDecimal getAge(){
		return age;
	}
	
	public int getAbundance() {
		return abundance;
	}
	
	public ArrayList<String> getOrbitalObjects() {
		return orbitalObjects;
	}

	public BigDecimal getMass() {
		return mass;
	}

	public void setOrbitalObjects(OrbitalObjects orbitalObjects) {

		this.orbitalObjects.add(orbitalObjects.getArchiveID());
	}

	//toString etc
	
	public String toString() {
		return "Star: "+ getName() + " (" + classification + ")";
	}


	@Override
	public BigDecimal getOrbitalDistance() {
		// TODO This should be implemented with multiple star system.
		return BigDecimal.valueOf(0);
	}








	
	
	

}
