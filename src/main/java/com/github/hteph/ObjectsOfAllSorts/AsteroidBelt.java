package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.util.Arrays;

public class AsteroidBelt extends OrbitalObjects{

	private static final long serialVersionUID = 1L;
	private double mass;	
	private double eccentricity;
	private String asterioidBeltType;
	private double asteroidBeltWidth;
	private String objectClass = "Asteroid Belt";
	
	private double[] sizeDistribution = new double[2]; // Average size/ Max size


	// Constructor ----------------------------------------------
	public AsteroidBelt(String archiveID, String name, String description, BigDecimal orbitDistance, StellarObject orbitingAround) {
		super(archiveID, name, description, orbitDistance, orbitingAround);
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters -------------------------------------

	public String getObjectClass() {
		return objectClass;
	}
	
	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public void setAsteroidBeltWidth(double asteroidBeltWidth) {
		this.asteroidBeltWidth = asteroidBeltWidth;
	}
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}	

	public double getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
	}

	public BigDecimal getOrbitalDistance() {
		return orbitDistanceStar;
	}

	public String getAsterioidBeltType() {
		return asterioidBeltType;
	}

	public void setAsterioidBeltType(String asterioidBeltType) {
		this.asterioidBeltType = asterioidBeltType;
	}

	public double getAsteroidBeltWidth() {
		return asteroidBeltWidth;
	}

	public void setAsteroidBeltWidht(double asteroidBeltWidth) {
		this.asteroidBeltWidth = asteroidBeltWidth;
	}

	public double[] getSizeDistribution() {
		return sizeDistribution;
	}

	public void setSizeDistribution(double average, double max) {
		
		this.sizeDistribution[0] = average;
		this.sizeDistribution[1] =max;
	}

	@Override
	public String toString() {
		return "AsteroidBelt [asterioidBeltType=" + asterioidBeltType + ", asteroidBeltWidth=" + asteroidBeltWidth
				+ ", sizeDistribution=" + Arrays.toString(sizeDistribution) + "]";
	}

}
