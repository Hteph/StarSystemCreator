package com.github.hteph.ObjectsOfAllSorts;

import com.github.hteph.Utilities.enums.Breathing;
import com.github.hteph.Utilities.enums.HydrosphereDescription;
import com.github.hteph.Utilities.comparators.lunarObjectDistanceComparator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Planet extends OrbitalObjects {

    private static final long serialVersionUID = 1L;
    private BigDecimal mass;
    private int radius;
    private BigDecimal gravity;
    private BigDecimal density;
    private BigDecimal orbitalPeriod;
    private BigDecimal axialTilt;
    private boolean tidelocked;
    private BigDecimal rotationalPeriod;
    private String tectonicCore;
    private BigDecimal magneticField;
    private HydrosphereDescription hydrosphereDescription;
    private int hydrosphere;
    private Set<AtmosphericGases> atmosphericComposition;
    private BigDecimal atmoPressure;
    private int surfaceTemp;
    private int[] rangeBandTemperature;
    private int[] rangeBandTempSummer;
    private int[] rangeBandTempWinter;
    private BigDecimal nightTempMod;
    private BigDecimal dayTempMod;
    private String tectonicActivityGroup;
    private BigDecimal orbitalInclination;
    private boolean boilingAtmo;
    private List<String> moonList;
    private BigDecimal lunarTidal;
    private boolean planetLocked;
    private BigDecimal lunarOrbitalPeriod;
    private BigDecimal lunarOrbitDistance; //in planetRadii
    private String classificationName;
    private Breathing lifeType;

    // Constructor ----------------------------------------------


    public Planet(Builder builder) {
        super(builder.archiveID,
              builder.name,
              builder.description,
              builder.orbitDistanceStar,
              builder.orbitingAround,
              builder.eccentricity,
              builder.orbitalObjectClass);
        this.mass = builder.mass;
        this.radius = builder.radius;
        this.gravity = builder.gravity;
        this.density = builder.density;
        this.orbitalPeriod = builder.orbitalPeriod;
        this.axialTilt = builder.axialTilt;
        this.tidelocked = builder.tidelocked;
        this.rotationalPeriod = builder.rotationalPeriod;
        this.tectonicCore = builder.tectonicCore;
        this.magneticField = builder.magneticField;
        this.hydrosphereDescription = builder.hydrosphereDescription;
        this.hydrosphere = builder.hydrosphere;
        this.atmosphericComposition = builder.atmosphericComposition;
        this.atmoPressure = builder.atmoPressure;
        this.surfaceTemp = builder.surfaceTemp;
        this.rangeBandTemperature = builder.rangeBandTemperature;
        this.rangeBandTempSummer = builder.rangeBandTempSummer;
        this.rangeBandTempWinter = builder.rangeBandTempWinter;
        this.nightTempMod = builder.nightTempMod;
        this.dayTempMod = builder.dayTempMod;
        this.tectonicActivityGroup = builder.tectonicActivityGroup;
        this.orbitalInclination = builder.orbitalInclination;
        this.boilingAtmo = builder.boilingAtmo;
        this.moonList = builder.moonList;
        this.lunarTidal = builder.lunarTidal;
        this.planetLocked = builder.planetLocked;
        this.lunarOrbitalPeriod = builder.lunarOrbitalPeriod;
        this.classificationName = builder.classificationName;
        this.lifeType = builder.lifeType;
        this.lunarOrbitDistance = builder.localOrbitDistance;
    }

    //Methods --------------------------------------------------

    @Override
    public String toString() {
        return "Planet " + super.getName() + ": " + super.getDescription() + ", radius=" + radius
                       + ", hydrosphereDescription=" + hydrosphereDescription + ", hydro%=" + hydrosphere + ", pressure="
                       + atmoPressure + ",\n surfaceTemp=" + surfaceTemp + ", lifeType=" + lifeType + "\n Atmo" + atmosphericComposition.toString();
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
        this.mass = BigDecimal.valueOf(mass).setScale(3, RoundingMode.HALF_UP);
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
        this.gravity = BigDecimal.valueOf(gravity).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = BigDecimal.valueOf(density).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = BigDecimal.valueOf(orbitalPeriod).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal getAxialTilt() {
        return axialTilt;
    }

    public void setAxialTilt(double axialTilt) {
        this.axialTilt = BigDecimal.valueOf(axialTilt).setScale(3,RoundingMode.HALF_UP);
    }

    public BigDecimal getEccentricity() {
        return super.getOrbitaleccentricity();
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
        this.rotationalPeriod = BigDecimal.valueOf(rotationalPeriod).setScale(3, RoundingMode.HALF_UP);
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

    public void setMagneticField(double magneticField) {
        this.magneticField = BigDecimal.valueOf(magneticField).setScale(3, RoundingMode.HALF_UP);
    }

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

        return atmosphericComposition.stream().map(AtmosphericGases::toString).collect(Collectors.joining(" "));
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
        this.orbitalInclination = BigDecimal.valueOf(orbitalInclination).setScale(3, RoundingMode.HALF_UP);
    }

    public Breathing getLifeType() {
        return lifeType;
    }

    public void setLifeType(Breathing lifeType) {
        this.lifeType = lifeType;
    }

    public BigDecimal getOrbitalDistance() {
        return super.getOrbitalDistance();
    }

    public String getClassificationName() {
        return classificationName;
    }

    public ArrayList<String> getLunarObjects() {
        return new ArrayList<>(moonList);
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public BigDecimal getLunarTidal() {
        return lunarTidal;
    }

    public void setLunarTidal(double lunarTidal) {
        this.lunarTidal = BigDecimal.valueOf(lunarTidal).setScale(3, BigDecimal.ROUND_HALF_UP);
    }

    public boolean isPlanetLocked() {
        return planetLocked;
    }

    public void setPlanetLocked(boolean planetLocked) {
        this.planetLocked = planetLocked;
    }

    public BigDecimal getLunarOrbitalPeriod() {

        return (lunarOrbitalPeriod == null) ? BigDecimal.valueOf(-1) : lunarOrbitalPeriod;
    }

    public void setLunarOrbitalPeriod(Double lunarOrbitalPeriod) {
        if (lunarOrbitalPeriod != null) this.lunarOrbitalPeriod = BigDecimal.valueOf(lunarOrbitalPeriod).setScale(3, RoundingMode.HALF_UP);
    }

//    public void addLunarObjects(OrbitalObjects newLunarObject) {
//
//        final int ASCII_STARTING_NUMBER = 97;
//        final int ASCII_ENDING_NUMBER = 122;
//
//        Set<OrbitalObjects> objectList = new TreeSet<>(new lunarObjectDistanceComparator());
//
//        for (String moonID : moonList) objectList.add((OrbitalObjects) CentralRegistry.getAndRemoveFromArchive(moonID));
//        wipeMoons();
//        objectList.add(newLunarObject);
//
//        char asciiNumber = ASCII_STARTING_NUMBER;
//        for (OrbitalObjects moon : objectList) {
//
//            String moonID = moon.getOrbitingAround().getArchiveID()
//                                    + "."
//                                    + asciiNumber
//                                    + moon.getLocalOrbitDistance();
//            moonList.add(moonID);
//            moon.setArchiveID(moonID);
//            CentralRegistry.putInArchive(moon);
//            asciiNumber++;
//            if (asciiNumber > ASCII_ENDING_NUMBER) asciiNumber = ASCII_STARTING_NUMBER;
//        }
//    }

	public BigDecimal getLunarOrbitDistance() {
		return lunarOrbitDistance;
	}

	public void setLunarOrbitDistance(double lunarOrbitDistance) {
		this.lunarOrbitDistance = BigDecimal.valueOf(lunarOrbitDistance).setScale(3, RoundingMode.HALF_UP);
	}

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        //super.super
        protected String name;
        protected String description;
        protected String archiveID;

        //super
        private BigDecimal orbitDistanceStar;
        private String orbitingAround;
        private BigDecimal eccentricity;
        private char orbitalObjectClass;
        private BigDecimal localOrbitDistance;

        //this
        private BigDecimal mass;
        private int radius;
        private BigDecimal gravity;
        private BigDecimal density;
        private BigDecimal orbitalPeriod;
        private BigDecimal axialTilt;
        private boolean tidelocked;
        private BigDecimal rotationalPeriod;
        private String tectonicCore;
        private BigDecimal magneticField;
        private HydrosphereDescription hydrosphereDescription;
        private int hydrosphere;
        private Set<AtmosphericGases> atmosphericComposition = new TreeSet<>(new AtmosphericGases.atmoCompositionComparator());
        private BigDecimal atmoPressure;
        private int surfaceTemp;
        private int[] rangeBandTemperature = new int[10];
        private int[] rangeBandTempSummer = new int[10];
        private int[] rangeBandTempWinter = new int[10];
        private BigDecimal nightTempMod;
        private BigDecimal dayTempMod;
        private String tectonicActivityGroup;
        private BigDecimal orbitalInclination;
        private boolean boilingAtmo;
        //private Set<StellarObject> lunarObjects = new TreeSet<>(new lunarObjectDistanceComparator());
        private List<String> moonList;
        private BigDecimal lunarTidal;
        private boolean planetLocked;
        private BigDecimal lunarOrbitalPeriod;
        //	private BigDecimal lunarOrbitDistance; //in planetRadii
        private String classificationName;
        private Breathing lifeType;

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

        public Builder withOrbitDistanceStar(BigDecimal orbitDistanceStar) {
            this.orbitDistanceStar = orbitDistanceStar;
            return this;
        }

        public Builder withOrbitingAround(String orbitingAround) {
            this.orbitingAround = orbitingAround;
            return this;
        }

        public Builder withEccentricity(BigDecimal eccentricity) {
            this.eccentricity = eccentricity;
            return this;
        }

        public Builder withOrbitalObjectClass(char orbitalObjectClass) {
            this.orbitalObjectClass = orbitalObjectClass;
            return this;
        }

        public Builder withLocalOrbitDistance(BigDecimal localOrbitDistance) {
            this.localOrbitDistance = localOrbitDistance;
            return this;
        }

        public Builder withMass(BigDecimal mass) {
            this.mass = mass;
            return this;
        }

        public Builder withRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder withGravity(BigDecimal gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder withDensity(BigDecimal density) {
            this.density = density;
            return this;
        }

        public Builder withOrbitalPeriod(BigDecimal orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
            return this;
        }

        public Builder withAxialTilt(BigDecimal axialTilt) {
            this.axialTilt = axialTilt;
            return this;
        }

        public Builder withTidelocked(boolean tidelocked) {
            this.tidelocked = tidelocked;
            return this;
        }

        public Builder withRotationalPeriod(BigDecimal rotationalPeriod) {
            this.rotationalPeriod = rotationalPeriod;
            return this;
        }

        public Builder withTectonicCore(String tectonicCore) {
            this.tectonicCore = tectonicCore;
            return this;
        }

        public Builder withMagneticField(BigDecimal magneticField) {
            this.magneticField = magneticField;
            return this;
        }

        public Builder withHydrosphereDescription(HydrosphereDescription hydrosphereDescription) {
            this.hydrosphereDescription = hydrosphereDescription;
            return this;
        }

        public Builder withHydrosphere(int hydrosphere) {
            this.hydrosphere = hydrosphere;
            return this;
        }

        public Builder withAtmosphericComposition(Set<AtmosphericGases> atmosphericComposition) {
            this.atmosphericComposition = atmosphericComposition;
            return this;
        }

        public Builder withAtmoPressure(BigDecimal atmoPressure) {
            this.atmoPressure = atmoPressure;
            return this;
        }

        public Builder withSurfaceTemp(int surfaceTemp) {
            this.surfaceTemp = surfaceTemp;
            return this;
        }

        public Builder withRangeBandTemperature(int[] rangeBandTemperature) {
            this.rangeBandTemperature = rangeBandTemperature;
            return this;
        }

        public Builder withRangeBandTempSummer(int[] rangeBandTempSummer) {
            this.rangeBandTempSummer = rangeBandTempSummer;
            return this;
        }

        public Builder withRangeBandTempWinter(int[] rangeBandTempWinter) {
            this.rangeBandTempWinter = rangeBandTempWinter;
            return this;
        }

        public Builder withNightTempMod(BigDecimal nightTempMod) {
            this.nightTempMod = nightTempMod;
            return this;
        }

        public Builder withDayTempMod(BigDecimal dayTempMod) {
            this.dayTempMod = dayTempMod;
            return this;
        }

        public Builder withTectonicActivityGroup(String tectonicActivityGroup) {
            this.tectonicActivityGroup = tectonicActivityGroup;
            return this;
        }

        public Builder withOrbitalInclination(BigDecimal orbitalInclination) {
            this.orbitalInclination = orbitalInclination;
            return this;
        }

        public Builder withBoilingAtmo(boolean boilingAtmo) {
            this.boilingAtmo = boilingAtmo;
            return this;
        }

        public Builder withMoonList(List<String> moonList) {
            this.moonList = moonList;
            return this;
        }

        public Builder withLunarTidal(BigDecimal lunarTidal) {
            this.lunarTidal = lunarTidal;
            return this;
        }

        public Builder withPlanetLocked(boolean planetLocked) {
            this.planetLocked = planetLocked;
            return this;
        }

        public Builder withLunarOrbitalPeriod(BigDecimal lunarOrbitalPeriod) {
            this.lunarOrbitalPeriod = lunarOrbitalPeriod;
            return this;
        }

        public Builder withClassificationName(String classificationName) {
            this.classificationName = classificationName;
            return this;
        }

        public Builder withLifeType(Breathing lifeType) {
            this.lifeType = lifeType;
            return this;
        }

        public Planet build() {return new Planet(this);}
    }
}
