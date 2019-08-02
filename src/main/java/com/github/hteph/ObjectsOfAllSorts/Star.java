package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Star extends StellarObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal lumosity;
	private BigDecimal diameter;
	private String classification;
	private BigDecimal age;
	private ArrayList<String> orbitalObjects = new ArrayList<>();
	private int abundance;
	private BigDecimal mass;

	// Constructor ------------------------------------------------------

	public Star(String archiveID, String name, String description, double lumosity, double mass, double diameter, String classification, double age, int abundance) {

		super(archiveID, name, description);

		this.lumosity = BigDecimal.valueOf(lumosity);
		this.diameter = BigDecimal.valueOf(diameter);
		this.classification = classification;
		this.age = BigDecimal.valueOf(age);
		this.abundance = abundance;
		this.mass = BigDecimal.valueOf(mass);
		orbitalObjects.add(this.getArchiveID()); //TODO this should use a Tree Set sorted on orbital distance instead
		CentralRegistry.putInArchive(this);
	}
	public Star(Builder builder) {

		super(builder.archiveID, builder.name, builder.description);

		this.lumosity = builder.lumosity;
		this.diameter = builder.diameter;
		this.classification = builder.classification;
		this.age = builder.age;
		this.abundance = builder.abundance;
		this.mass = builder.mass;
		orbitalObjects.add(this.getArchiveID()); //put the star as the first object in ther orbitals list
		CentralRegistry.putInArchive(this);
	}


	//Getters and Setter

	public BigDecimal getLumosity() {
		return lumosity;
	}

	public BigDecimal getDiameter() {
		return diameter;
	}

	public String getClassification() {
		return classification;
	}

	public BigDecimal getAge() {
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

	//Methods

	public void addOrbitalObjects(OrbitalObjects orbitalObjects) {

		this.orbitalObjects.add(orbitalObjects.getArchiveID());
	}

	//toString etc

	public String toString() {
		return "Star: " + getName() + " (" + classification + ")";
	}


	@Override
	public BigDecimal getOrbitalDistance() {
		// TODO This should be implemented with multiple star system.
		return BigDecimal.valueOf(0);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private BigDecimal lumosity;
		private BigDecimal diameter;
		private String classification;
		private BigDecimal age;
		private int abundance;
		private BigDecimal mass;
		private String name;
		private String description;
		private String archiveID;

		private Builder() {
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

		public Builder withLumosity(double lumosity) {
			this.lumosity = BigDecimal.valueOf(lumosity).setScale(3, RoundingMode.HALF_UP);
			return this;
		}

		public Builder withDiameter(double diameter) {
			this.diameter = BigDecimal.valueOf(diameter).setScale(3, RoundingMode.HALF_UP);
			return this;
		}

		public Builder withClassification(String classification) {
			this.classification = classification;
			return this;
		}

		public Builder withAge(double age) {
			this.age = BigDecimal.valueOf(age).setScale(3, RoundingMode.HALF_UP);
			return this;
		}

		public Builder withAbundance(int abundance) {
			this.abundance = abundance;
			return this;
		}

		public Builder withMass(double mass) {
			this.mass = BigDecimal.valueOf(mass).setScale(3, RoundingMode.HALF_UP);
			return this;
		}

		public Star build() {return new Star(this);}
	}
}