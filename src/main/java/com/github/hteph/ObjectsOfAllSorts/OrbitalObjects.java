package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;

public class OrbitalObjects extends StellarObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BigDecimal orbitDistanceStar;
	protected StellarObject orbitingAround;
	protected double orbitaleccentricity;
	protected char orbitalObjectClass;
	protected BigDecimal localOrbitDistance;
		
	//Constructor ----------------------------------------------------
	

	public OrbitalObjects(String archiveID, String name, String description, BigDecimal orbitDistanceStar, StellarObject orbitingAround) {
		super(name, description, archiveID);
		this.orbitDistanceStar = orbitDistanceStar;
		this.orbitingAround =orbitingAround;
	}
	public BigDecimal getLocalOrbitDistance() {
		return localOrbitDistance;
	}

	public void setLocalOrbitDistance(BigDecimal localOrbitDistance) {
		this.localOrbitDistance = localOrbitDistance;
	}
	public BigDecimal getOrbitalDistance() {
		return orbitDistanceStar;
	}

	public double getOrbitaleccentricity() {
		return orbitaleccentricity;
	}

	public char getOrbitalObjectClass() {
		return orbitalObjectClass;
	}

	public StellarObject getOrbitingAround() {
		return orbitingAround;
	}

}
