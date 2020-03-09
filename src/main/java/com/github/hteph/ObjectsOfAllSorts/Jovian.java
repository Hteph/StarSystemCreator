package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.github.hteph.Utilities.enums.Breathing;
import com.github.hteph.Utilities.NumberUtilities;

public class Jovian extends OrbitalObjects {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int radius;
	private BigDecimal mass;
	private double orbitalPeriod; //in earth years
	private double axialTilt;

	private String classificationName;
	private double rotationalPeriod;

	private double magneticField;
	private int baseTemperature;

	private double orbitalInclination;
	private Breathing lifeType;
	private ArrayList<StellarObject> lunarObjects;

	public Jovian(Builder builder) {

		super(builder.archiveID, builder.name, builder.description, builder.orbitDistanceStar, builder.orbitingAround, builder.orbitaleccentricity, builder.orbitalObjectClass);
		this.radius = builder.radius;
		this.mass = builder.mass;
		this.orbitalPeriod = builder.orbitalPeriod;
		this.axialTilt = builder.axialTilt;
		this.classificationName = builder.classificationName;
		this.rotationalPeriod = builder.rotationalPeriod;
		this.magneticField = builder.magneticField;
		this.baseTemperature = builder.baseTemperature;
		this.orbitalInclination = builder.orbitalInclination;
		this.lifeType = builder.lifeType;
		this.lunarObjects = builder.lunarObjects;
	}

	@Override
	public String toString() {
		return super.getName() + ", Mass = " + mass;
	}

	// Getters and Setters -------------------------------------
	public double getMass() {
		return mass.doubleValue();
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(double orbitalPeriod) {
		this.orbitalPeriod = NumberUtilities.nicefyDouble(orbitalPeriod);
	}

	public double getAxialTilt() {
		return axialTilt;
	}

	public void setAxialTilt(double axialTilt) {
		this.axialTilt = axialTilt;
	}

	public double getRotationalPeriod() {
		return rotationalPeriod;
	}

	public void setRotationalPeriod(double rotationalPeriod) {
		this.rotationalPeriod = rotationalPeriod;
	}

	public double getMagneticField() {
		return magneticField;
	}

	public void setMagneticField(double magneticField) {
		this.magneticField = NumberUtilities.nicefyDouble(magneticField);
	}

	public int getBaseTemperature() {
		return baseTemperature;
	}

	public void setBaseTemperature(int baseTemperature) {
		this.baseTemperature = baseTemperature;
	}

		public double getOrbitalInclination() {
		return orbitalInclination;
	}

	public void setOrbitalInclination(double orbitalInclination) {
		this.orbitalInclination = orbitalInclination;
	}

	public Breathing getLifeType() {
		return lifeType;
	}

	public void setLifeType(Breathing lifeType) {
		this.lifeType = lifeType;
	}

	public void setMass(double mass) {
		this.mass = BigDecimal.valueOf(mass).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public String getClassificationName() {
		return classificationName;
	}

	public ArrayList<StellarObject> getLunarObjects() {
		return lunarObjects;
	}

	public void setLunarObjects(ArrayList<StellarObject> lunarObjects) {
		this.lunarObjects = lunarObjects;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public static Jovian.Builder builder() {
		return new Jovian.Builder();
	}

	public static final class Builder {

		private int radius;
		private BigDecimal mass;
		private double orbitalPeriod; //in earth years
		private double axialTilt;

		private String classificationName;
		private double rotationalPeriod;

		private double magneticField;
		private int baseTemperature;

		private double orbitalInclination;
		private Breathing lifeType;
		private ArrayList<StellarObject> lunarObjects;

		private BigDecimal orbitDistanceStar;
		private StellarObject orbitingAround;
		private BigDecimal orbitaleccentricity;
		private char orbitalObjectClass;

		private String name;

		private String description;

		private String archiveID;


		public Builder withOrbitDistanceStar(BigDecimal orbitDistanceStar) {
			this.orbitDistanceStar = orbitDistanceStar;
			return this;
		}


		public Builder withOrbitingAround(StellarObject orbitingAround) {
			this.orbitingAround = orbitingAround;
			return this;
		}


		public Builder withOrbitaleccentricity(BigDecimal orbitaleccentricity) {
			this.orbitaleccentricity = orbitaleccentricity;
			return this;
		}


		public Builder withOrbitalObjectClass(char orbitalObjectClass) {
			this.orbitalObjectClass = orbitalObjectClass;
			return this;
		}


		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}


		public Builder withArchiveID(String archiveID) {
			this.archiveID = archiveID;
			return this;
		}

		public Builder withRadius(int radius) {
			this.radius = radius;
			return this;
		}

		public Builder withMass(BigDecimal mass) {
			this.mass = mass;
			return this;
		}

		public Builder withOrbitalPeriod(double orbitalPeriod) {
			this.orbitalPeriod = orbitalPeriod;
			return this;
		}

		public Builder withAxialTilt(double axialTilt) {
			this.axialTilt = axialTilt;
			return this;
		}

		public Builder withClassificationName(String classificationName) {
			this.classificationName = classificationName;
			return this;
		}

		public Builder withRotationalPeriod(double rotationalPeriod) {
			this.rotationalPeriod = rotationalPeriod;
			return this;
		}

		public Builder withMagneticField(double magneticField) {
			this.magneticField = magneticField;
			return this;
		}

		public Builder withBaseTemperature(int baseTemperature) {
			this.baseTemperature = baseTemperature;
			return this;
		}

		public Builder withOrbitalInclination(double orbitalInclination) {
			this.orbitalInclination = orbitalInclination;
			return this;
		}

		public Builder withLifeType(Breathing lifeType) {
			this.lifeType = lifeType;
			return this;
		}

		public Builder withLunarObjects(ArrayList<StellarObject> lunarObjects) {
			this.lunarObjects = lunarObjects;
			return this;
		}

		public Jovian build() {return new Jovian(this);}
	}
}
