package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;

public class OrbitalObjects extends StellarObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal orbitDistanceStar;
	private StellarObject orbitingAround;
	private BigDecimal orbitaleccentricity;
	private char orbitalObjectClass;
	private BigDecimal localOrbitDistance;
		
	//Constructor ----------------------------------------------------
	

	public OrbitalObjects(String archiveID, String name, String description, BigDecimal orbitDistanceStar, StellarObject orbitingAround, BigDecimal eccentricity, char orbitalObjectClass) {
		super(name, description, archiveID);
		this.orbitDistanceStar = orbitDistanceStar;
		this.orbitingAround =orbitingAround;
		this.orbitaleccentricity = eccentricity;
		this.orbitalObjectClass = orbitalObjectClass;
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

	public BigDecimal getOrbitaleccentricity() {
		return orbitaleccentricity;
	}

	public char getOrbitalObjectClass() {
		return orbitalObjectClass;
	}

	public StellarObject getOrbitingAround() {
		return orbitingAround;
	}

}
