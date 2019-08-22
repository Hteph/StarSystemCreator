package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.github.hteph.Generators.GenerateTerrestrialPlanet;
import com.github.hteph.Utilities.enums.Breathing;
import com.github.hteph.Utilities.enums.HydrosphereDescription;
import com.github.hteph.Utilities.lunarObjectDistanceComparator;

public class Planet extends OrbitalObjects {

	private static final long serialVersionUID = 1L;
	private BigDecimal mass;
	private int radius;
	private BigDecimal gravity;
	private BigDecimal density;
	private BigDecimal orbitalPeriod;
	private BigDecimal axialTilt;
	private BigDecimal eccentricity;
	private boolean tidelocked;
	private BigDecimal rotationalPeriod;
	private String tectonicCore;
	private BigDecimal magneticField;
	private HydrosphereDescription hydrosphereDescription;
	private int hydrosphere;
	private Set<AtmosphericGases> atmosphericComposition = new TreeSet<>(new AtmosphericGases.atmoCompositionComparator());
	private BigDecimal atmoPressure;
	private int surfaceTemp;
	private int[] rangeBandTemperature =new int[10];
	private int[] rangeBandTempSummer =new int[10];
	private int[] rangeBandTempWinter =new int[10];
	private BigDecimal nightTempMod;
	private BigDecimal dayTempMod;
	private String tectonicActivityGroup;
	private BigDecimal orbitalInclination;
	private boolean boilingAtmo;
	//private Set<StellarObject> lunarObjects = new TreeSet<>(new lunarObjectDistanceComparator());
	private ArrayList<String> moonList = new ArrayList<>();
	private BigDecimal lunarTidal;
	private boolean planetLocked;
	private BigDecimal lunarOrbitalPeriod;
//	private BigDecimal lunarOrbitDistance; //in planetRadii
	private String classificationName;
	private Breathing lifeType;

	// Constructor ----------------------------------------------
	public Planet(String archiveID, String name, String description, String classificationName, BigDecimal orbitDistance, StellarObject orbitingAround) {
		super(archiveID, name, description, orbitDistance, orbitingAround);
		this.classificationName=classificationName;
	}
	//Methods --------------------------------------------------

	@Override
	public String toString() {
		return "Planet " + name + ": " + description + ", radius=" + radius
				+ ", hydrosphereDescription=" + hydrosphereDescription +", hydro%="+hydrosphere+ ", pressure="
				+ atmoPressure + ",\n surfaceTemp=" + surfaceTemp + ", lifeType=" + lifeType + "\n Atmo"+ atmosphericComposition.toString();
	}

	public void wipeMoons() {
		moonList.removeAll(getLunarObjects());
	}

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
	public boolean isBoilingAtmo() {
		return boilingAtmo;
	}

	public void setBoilingAtmo(boolean boilingAtmo) {
		this.boilingAtmo = boilingAtmo;
	}

	public BigDecimal getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = BigDecimal.valueOf(mass).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public BigDecimal getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = BigDecimal.valueOf(gravity).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = BigDecimal.valueOf(density).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(double orbitalPeriod) {
		this.orbitalPeriod = BigDecimal.valueOf(orbitalPeriod).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getAxialTilt() {
		return axialTilt;
	}

	public void setAxialTilt(double axialTilt) {
		this.axialTilt = BigDecimal.valueOf(axialTilt).setScale(3);
	}

	public BigDecimal getEccentricity() {return eccentricity;}

	public void setEccentricity(double eccentricity) {
		this.eccentricity = BigDecimal.valueOf(eccentricity).setScale(3);
	}

	public boolean isTidelocked() {
		return tidelocked;
	}

	public void setTidelocked(boolean tidelocked) {
		this.tidelocked = tidelocked;
	}

	public BigDecimal getRotationalPeriod() {
		return rotationalPeriod;
	}

	public void setRotationalPeriod(double rotationalPeriod) {
		this.rotationalPeriod = BigDecimal.valueOf(rotationalPeriod).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public String getTectonicCore() {
		return tectonicCore;
	}

	public void setTectonicCore(String tectonicCore) {
		this.tectonicCore = tectonicCore;
	}

	public BigDecimal getMagneticField() {
		return magneticField;
	}

	public void setMagneticField(double magneticField) {this.magneticField = BigDecimal.valueOf(magneticField).setScale(3,BigDecimal.ROUND_HALF_UP); }

	public HydrosphereDescription getHydrosphereDescription() {
		return hydrosphereDescription;
	}

	public void setHydrosphereDescription(HydrosphereDescription hydrosphereDescription) {
		this.hydrosphereDescription = hydrosphereDescription;
	}

	public int getHydrosphere() {
		return hydrosphere;
	}

	public void setHydrosphere(int hydrosphere) {
		this.hydrosphere = hydrosphere;
	}

	public Set<AtmosphericGases> getAtmosphericComposition() {
		return atmosphericComposition;
	}

	public String getAtmosphericCompositionParsed() {

		return atmosphericComposition.stream().map(AtmosphericGases::toString).collect(Collectors.joining(" " ) );
	}

	public void setAtmosphericComposition(Set<AtmosphericGases> atmosphericComposition) {

		this.atmosphericComposition = atmosphericComposition;
	}

	public BigDecimal getAtmoPressure() {
		return atmoPressure;
	}

	public void setAtmoPressure(double atmoPressure) {
		this.atmoPressure = BigDecimal.valueOf(atmoPressure).setScale(3, RoundingMode.HALF_UP);
	}

	public double getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(int surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}

	public int[] getRangeBandTemperature() {
		return rangeBandTemperature;
	}

	public void setRangeBandTemperature(int[] rangeBandTemperature) {
		this.rangeBandTemperature = rangeBandTemperature;
	}

	public int[] getRangeBandTempSummer() {
		return rangeBandTempSummer;
	}

	public void setRangeBandTempSummer(int[] rangeBandTempSummer) {
		this.rangeBandTempSummer = rangeBandTempSummer;
	}

	public int[] getRangeBandTempWinter() {
		return rangeBandTempWinter;
	}

	public void setRangeBandTempWinter(int[] rangeBandTempWinter) {
		this.rangeBandTempWinter = rangeBandTempWinter;
	}

	public BigDecimal getNightTempMod() {
		return nightTempMod;
	}

	public void setNightTempMod(double nightTempMod) {
		this.nightTempMod = BigDecimal.valueOf(nightTempMod);
	}

	public BigDecimal getDayTempMod() {
		return dayTempMod;
	}

	public void setDayTempMod(double dayTempMod) {
		this.dayTempMod = BigDecimal.valueOf(dayTempMod).setScale(3, RoundingMode.HALF_UP);
	}

	public String getTectonicActivityGroup() {
		return tectonicActivityGroup;
	}

	public void setTectonicActivityGroup(String tectonicActivityGroup) {
		this.tectonicActivityGroup = tectonicActivityGroup;
	}

	public BigDecimal getOrbitalInclination() {
		return orbitalInclination;
	}

	public void setOrbitalInclination(double orbitalInclination) {
		this.orbitalInclination = BigDecimal.valueOf(orbitalInclination).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public Breathing getLifeType() {
		return lifeType;
	}

	public void setLifeType(Breathing lifeType) {
		this.lifeType = lifeType;
	}

	public BigDecimal getOrbitalDistance() {
		return orbitDistanceStar;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public ArrayList<String> getLunarObjects() {return new ArrayList<>(moonList);}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public BigDecimal getLunarTidal() {
		return lunarTidal;
	}

	public void setLunarTidal(double lunarTidal) {
		this.lunarTidal = BigDecimal.valueOf(lunarTidal).setScale(3,BigDecimal.ROUND_HALF_UP);
	}

	public boolean isPlanetLocked() {
		return planetLocked;
	}

	public void setPlanetLocked(boolean planetLocked) {
		this.planetLocked = planetLocked;
	}

	public BigDecimal getLunarOrbitalPeriod() {

		if(lunarOrbitalPeriod==null)return BigDecimal.valueOf(-1);

		return lunarOrbitalPeriod;
	}

	public void setLunarOrbitalPeriod(double lunarOrbitalPeriod) {
		this.lunarOrbitalPeriod = BigDecimal.valueOf(lunarOrbitalPeriod).setScale(3, RoundingMode.HALF_UP);
	}

	public void addLunarObjects(OrbitalObjects lunarObject) {

		final int ASCII_STARTING_NUMBER = 97;
		final int ASCII_ENDING_NUMBER = 122;

		Set<OrbitalObjects> objectList = new TreeSet<>(new lunarObjectDistanceComparator());

		for (String moonID:moonList) objectList.add((OrbitalObjects)CentralRegistry.getAndRemoveFromArchive(moonID));
		wipeMoons();
		objectList.add(lunarObject);

		char asciiNumber = ASCII_STARTING_NUMBER;
		for (OrbitalObjects moon:objectList){

			String moonID = moon.getOrbitingAround().getArchiveID()
					+ "."
					+ asciiNumber
					+ moon.getLocalOrbitDistance();
			moonList.add(moonID);
			moon.setArchiveID(moonID);
			CentralRegistry.putInArchive(moon);
			asciiNumber++;
			if(asciiNumber>ASCII_ENDING_NUMBER)  asciiNumber=ASCII_STARTING_NUMBER;
		}
	}

	public BigDecimal getLunarOrbitDistance() {
		return localOrbitDistance;
	}

	public void setLunarOrbitDistance(double lunarOrbitDistance) {
		this.localOrbitDistance = BigDecimal.valueOf(lunarOrbitDistance).setScale(3, RoundingMode.HALF_UP);
	}
}
