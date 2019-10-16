package com.github.hteph.Generators;


import com.github.hteph.ObjectsOfAllSorts.*;
import com.github.hteph.Tables.FindAtmoPressure;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Tables.TectonicActivityTable;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NumberUtilities;
import com.github.hteph.Utilities.enums.Breathing;
import com.github.hteph.Utilities.enums.HydrosphereDescription;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.github.hteph.Utilities.NumberUtilities.*;

public final class GenerateTerrestrialPlanet {


    private GenerateTerrestrialPlanet() {
        // No instances should be created by this method
    }


    public static OrbitalObjects Generator(String archiveID,
                                           String name,
                                           String description,
                                           String classificationName,
                                           BigDecimal orbitDistance,
                                           char orbitalObjectClass,
                                           StellarObject centralObject,
                                           double lunarOrbitDistance) {

        //Last parameter (lunarOrbitDistance) is only used if this is a moon to be created
        double mass;
        double gravity;
        double density;
        double orbitalPeriod; //in earth years
        double axialTilt;
        double eccentricity;
        boolean tidelocked = false;
        boolean planetLocked = false;
        //boolean moonLocked = false; //Not implemented yet
        double rotationalPeriod;
        double tectonicActivity;
        String tectonicCore;
        double magneticField;
        int baseTemperature;
        HydrosphereDescription hydrosphereDescription;
        int hydrosphere;
        int waterVaporFactor;
        TreeSet<AtmosphericGases> atmoshericComposition;
        BigDecimal atmoPressure;
        double albedo;
        double greenhouseFactor;
        boolean InnerZone = false;
        String tectonicActivityGroup;
        double orbitalInclination;
        OrbitalObjects moonsPlanet = null;
        double moonOrbit = 0; //in planetary radii
        double moonsPlanetMass = 0;
        double lunarOrbitalPeriod = 0;
        double moonTidal;
        double moonsPlanetsRadii = 0;

        boolean hasGaia;
        Breathing lifeType;
        Star orbitingAround;
        double tidalForce = 0;
        double tidelock;
        double sumOfLunarTidal = 0;

        //Ugly hack to be able to reuse this method for generating moons
        if (String.valueOf(orbitalObjectClass).equalsIgnoreCase("m")) {
            if (centralObject instanceof Planet) {
                moonsPlanet = (Planet) centralObject;
                moonsPlanetMass = ((Planet) moonsPlanet).getMass().doubleValue();
                moonsPlanetsRadii = ((Planet) moonsPlanet).getRadius();
            } else {
                moonsPlanet = (Jovian) centralObject;
                moonsPlanetMass = ((Jovian) centralObject).getMass();
                moonsPlanetsRadii = ((Jovian) centralObject).getRadius();
            }

            orbitingAround = (Star) moonsPlanet.getOrbitingAround();
            orbitDistance = moonsPlanet.getOrbitalDistance();

        } else orbitingAround = (Star) centralObject;

        Planet planet = new Planet(archiveID, name, description, classificationName, orbitDistance, orbitingAround);
        if (String.valueOf(orbitalObjectClass).equalsIgnoreCase("m")) planet.setLunarOrbitDistance(lunarOrbitDistance);

        double snowLine = 5 * sqrt(orbitingAround.getLumosity().doubleValue());
        if (orbitDistance.doubleValue() < snowLine) InnerZone = true;

        // size may not be all, but here it is set
        //TODO add greater varity for moon objects, depending on planet

        int baseSize = 900; //Default for planets
        if (orbitalObjectClass == 'M') {
            List<Integer> baseSizeList = Arrays.asList(100, 200, 300, 400, 500, 600, 700);
            TableMaker.makeRoll(Dice.d10(), baseSizeList);
            if (moonsPlanet instanceof Planet) baseSize = Math.min(baseSize, ((Planet) moonsPlanet).getRadius() / 8);
        }else if(orbitalObjectClass == 'm'){
            List<Integer> baseSizeList = Arrays.asList(1, 5, 10, 20, 30, 40, 50);
            TableMaker.makeRoll(Dice.d10(), baseSizeList);
            if (moonsPlanet instanceof Planet) baseSize = Math.min(baseSize, ((Planet) moonsPlanet).getRadius() / 8);
    }else if (orbitalObjectClass == 't' || orbitalObjectClass == 'c') baseSize = 90;

        planet.setRadius(Dice._2d6() * baseSize);

        //density
        if (orbitDistance.doubleValue() < snowLine) {
            density = 0.3 + (Dice._2d6() - 2) * 0.127 / Math.pow(
                    0.4 + (orbitDistance.doubleValue() / sqrt(orbitingAround.getLumosity().doubleValue())), 0.67);
        } else {
            density = 0.3 + (Dice._2d6() - 2) * 0.05;
        }
        mass = cubed(planet.getRadius() / 6380.0) * density;
        gravity = mass / squared((planet.getRadius() / 6380.0));

        if (orbitalObjectClass == 'm') {
            lunarOrbitalPeriod = sqrt(cubed((moonOrbit * moonsPlanetsRadii) / 400000) * 793.64 / (moonsPlanetMass + mass));
        }
        orbitalPeriod = sqrt(cubed(orbitDistance.doubleValue()) / orbitingAround.getMass().doubleValue()); //in earth days

        planet.setMass(mass);
        planet.setDensity(density);
        planet.setGravity(gravity);
        planet.setOrbitalPeriod(orbitalPeriod);
        planet.setLunarOrbitalPeriod(lunarOrbitalPeriod);

        //Eccentricity and Inclination

        int eccentryMod = 1;
        if (orbitalObjectClass == 'C' || orbitalObjectClass == 'c') eccentryMod += 3;
        eccentricity = eccentryMod * (Dice._2d6() - 2) / (100.0 * Dice.d6());
        axialTilt = (int) (10 * Dice._3d6() / 2.0 * Math.random());
        orbitalInclination = eccentryMod * (Dice._2d6()) / (1.0 + mass / 10.0);
        planet.setAxialTilt(axialTilt);
        planet.setOrbitalInclination(orbitalInclination);

        // TODO tidelocked or not should take into consideration moons too!

        if (!String.valueOf(orbitalObjectClass).equalsIgnoreCase("m")) GenerateMoon.createMoons(planet, InnerZone);

        if (planet.getLunarObjects().size() > 0) {
            double[] lunartidal = new double[planet.getLunarObjects().size()];
            for (int n = 0; n < planet.getLunarObjects().size(); n++) {
                for (String moonID : planet.getLunarObjects()) {
                    OrbitalObjects moon = (OrbitalObjects) CentralRegistry.getFromArchive(moonID);

                    if (moon instanceof Planet) {
                        lunartidal[n] = calcLunarTidal(planet, (Planet) moon);
                        ((Planet) moon).setLunarTidal(lunartidal[n]);
                    }
                }
            }
            sumOfLunarTidal = DoubleStream.of(lunartidal).sum();
        }

        if (orbitalObjectClass == 'm') {

            int baseRadius;
            if (moonsPlanet instanceof Planet) baseRadius = ((Planet) moonsPlanet).getRadius();
            else baseRadius = ((Jovian) moonsPlanet).getRadius();

            moonTidal = moonsPlanetMass * 26640000 / 333000.0 / Math.pow(baseRadius * moonOrbit * 400 / 149600000, 3);
            if (moonTidal > 1) planetLocked = true;
            planet.setPlanetLocked(true);
        } else {
            tidalForce = (orbitingAround.getMass().doubleValue() * 26640000 / cubed(orbitDistance.doubleValue() * 400))
                         / (1.0 + sumOfLunarTidal);
            tidelock = (0.83 + (Dice._2d6() - 2) * 0.03) * tidalForce * orbitingAround.getAge().doubleValue() / 6.6;
            if (tidelock > 1) {
                tidelocked = true;
                planet.wipeMoons(); //TODO Tidelocked planets generally can't have moon, but catched objects should
                // be allowed?
            }
        }

        //Rotation - day/night cycle
        if (tidelocked) {
            rotationalPeriod = orbitalPeriod * 365;
        } else if (planetLocked) {
            rotationalPeriod = lunarOrbitalPeriod;
        } else {
            rotationalPeriod = (Dice.d6() + Dice.d6() + 8) * (1 + 0.1 * (tidalForce * orbitingAround.getAge()
                                                                                                    .doubleValue() -
                                                                         Math.pow(
                                                                                 mass,
                                                                                 0.5)));
            if (Dice.d6() < 2) rotationalPeriod = Math.pow(rotationalPeriod, Dice.d6());

            if (rotationalPeriod > orbitalPeriod / 2.0) {

                double[] resonanceArray = {0.5, 2 / 3.0, 1, 1.5, 2, 2.5, 3, 3.5};
                double[] eccentricityEffect = {0.1, 0.15, 0.21, 0.39, 0.57, 0.72, 0.87, 0.87};

                int resultResonance = Arrays.binarySearch(resonanceArray, rotationalPeriod / orbitalPeriod);

                if (resultResonance < 0) {
                    eccentricity = eccentricityEffect[-resultResonance - 2];
                    rotationalPeriod = resonanceArray[-resultResonance - 2];
                } else {
                    resultResonance = Math.min(resultResonance,
                                               6); //TODO there is somethng fishy here, Edge case of greater than
                    // 0.87 relation still causes problem Should rethink methodology
                    eccentricity = eccentricityEffect[resultResonance];
                    rotationalPeriod = resonanceArray[-resultResonance];
                }
            }
        }

        planet.setEccentricity(eccentricity);
        planet.setRotationalPeriod(rotationalPeriod);

        //TODO tectonics should include moons!

        //TODO c type should have special treatment

        tectonicCore = findTectonicGroup(InnerZone, density);
        tectonicActivity = (5 + Dice._2d6() - 2) * Math.pow(mass, 0.5) / orbitingAround.getAge().doubleValue();
        tectonicActivity *= (1 + 0.5 * tidalForce);
        tectonicActivityGroup = TectonicActivityTable.findTectonicActivityGroup(tectonicActivity);

        planet.setTectonicCore(tectonicCore);
        planet.setTectonicActivityGroup(tectonicActivityGroup);

        //ditching tectonicActivitynumber as it is a value of little interest further on.

        //Magnetic field
        if (tectonicCore.contains("metal")) {
            magneticField = 10
                            / (sqrt((rotationalPeriod / 24.0)))
                            * squared(density)
                            * sqrt(mass)
                            / orbitingAround.getAge().doubleValue();
            if (tectonicCore.contains("small")) magneticField *= 0.5;
            if (tectonicCore.contains("medium")) magneticField *= 0.75;
            if (tectonicActivityGroup.equals("Dead")) magneticField = Dice.d6() / 15.0;
        } else {
            magneticField = Dice.d6() / 20.0;
        }
        planet.setMagneticField(magneticField);

        //Temperature
        baseTemperature = (int) (255 / sqrt((orbitDistance.doubleValue() / sqrt(orbitingAround.getLumosity().doubleValue()))));

        //base temp is an value of little use beyond this generator and is not propagated to the planet object

        //Hydrosphere
        hydrosphereDescription = findHydrosphereDescription(InnerZone, baseTemperature);
        hydrosphere = findTheHydrosphere(hydrosphereDescription, planet.getRadius());
        if (hydrosphereDescription.equals(HydrosphereDescription.LIQUID) || hydrosphereDescription.equals(
                HydrosphereDescription.ICE_SHEET)) {
            waterVaporFactor = Math.max(0, (baseTemperature - 240) / 100 * hydrosphere * (Dice._2d6() - 1));
        } else {
            waterVaporFactor = 0;
        }

        planet.setHydrosphereDescription(hydrosphereDescription);
        planet.setHydrosphere(hydrosphere);

        //Atmoshperic details
        atmoshericComposition = makeAtmosphere.createPlanetary(orbitingAround,
                                                               baseTemperature,
                                                               tectonicActivityGroup,
                                                               planet.getRadius(),
                                                               gravity,
                                                               planet);
        atmoPressure = FindAtmoPressure.findAtmoPressure(tectonicActivityGroup,
                                                         hydrosphere,
                                                         planet.isBoilingAtmo(),
                                                         mass,
                                                         atmoshericComposition);

        // TODO Special considerations for c objects, this should be expanded upon when these gets more details

        if (orbitalObjectClass == 'c') { //These should never had a chance to get an "real" atmosphere in the first
            // place but may have some traces
            if (Dice.d6() < 6) atmoPressure = BigDecimal.valueOf(0);
            else atmoPressure = BigDecimal.valueOf(0.001);
        }

        if (atmoPressure.doubleValue() == 0) atmoshericComposition.clear();
        if (atmoshericComposition.size() == 0) { //There are edge cases where all of atmo has boiled away
            atmoPressure = BigDecimal.valueOf(0);
            planet.setHydrosphereDescription(HydrosphereDescription.REMNANTS);
            planet.setHydrosphere(0);
        }

        planet.setAtmoPressure(atmoPressure.doubleValue());
        // The composition could be adjusted for the existence of life, so is set below

        //Bioshpere
        hasGaia = testLife(baseTemperature, atmoPressure.doubleValue(), hydrosphere, atmoshericComposition);
        if (hasGaia) lifeType = findLifeType(atmoshericComposition);
        else lifeType = Breathing.NONE;

        if (lifeType.equals(Breathing.OXYGEN)) adjustForOxygen(atmoPressure.doubleValue(), atmoshericComposition);

        albedo = findAlbedo(InnerZone, atmoPressure.doubleValue(), hydrosphereDescription, planet);
        double greenhouseGasEffect = findGreenhouseGases(atmoshericComposition, atmoPressure.doubleValue());

        greenhouseFactor = 1
                           + sqrt(atmoPressure.doubleValue()) * 0.01 * (Dice._2d6() - 1)
                           + sqrt(greenhouseGasEffect) * 0.1
                           + waterVaporFactor * 0.1;

        //TODO Here adding some Gaia moderation factor (needs tweaking probably) moving a bit more towards
        // water/carbon ideal
        if (lifeType.equals(Breathing.OXYGEN) && baseTemperature > 350) greenhouseFactor *= 0.8;
        if (lifeType.equals(Breathing.OXYGEN) && baseTemperature < 250) greenhouseFactor *= 1.2;

        planet.setAtmosphericComposition(atmoshericComposition);
        planet.setLifeType(lifeType);
        planet.setSurfaceTemp(getSurfaceTemp(baseTemperature, atmoPressure, albedo, greenhouseFactor, hasGaia));

        //Climate -------------------------------------------------------
        // sets all the temperature stuff from axial tilt etc etc
        setAllKindOfLocalTemperature(planet,
                                     atmoPressure.doubleValue(),
                                     hydrosphere,
                                     rotationalPeriod,
                                     axialTilt,
                                     planet.getSurfaceTemp(),
                                     orbitalPeriod); // sets all the temperature stuff from axial tilt etc etc

        //TODO Weather and day night temp cycle
        // and here we return the result
        return planet;
    }

    private static int getSurfaceTemp(int baseTemperature,
                                      BigDecimal atmoPressure,
                                      double albedo,
                                      double greenhouseFactor,
                                      boolean hasGaia) {

        // My take on the effect of greenhouse and albedo on temperature max planerary temp is 1000 and the half
        // point is 400
        double surfaceTemp;
        if (hasGaia)
            surfaceTemp = 400 * (baseTemperature * albedo * greenhouseFactor) / (350 + baseTemperature * albedo *
                                                                                       greenhouseFactor);
        else if (atmoPressure.doubleValue() > 0)
            surfaceTemp = 800 * (baseTemperature * albedo * greenhouseFactor) / (400 + baseTemperature * albedo *
                                                                                       greenhouseFactor);
        else
            surfaceTemp = 1200 * (baseTemperature * albedo * greenhouseFactor) / (800 + baseTemperature * albedo *
                                                                                        greenhouseFactor);
        return (int) surfaceTemp;
    }

    // Inner methods -------------------------------------------------------------------------------------------------
    private static double calcLunarTidal(Planet planetEffectOn, Planet planetEffectOf) {

        return planetEffectOf.getMass().doubleValue()
               * 26640000
               / 333000.0
               / Math.pow(planetEffectOn.getRadius()
                          * planetEffectOf.getLunarOrbitDistance().doubleValue()
                          * 400
                          / 149600000
                , 3);
    }

    private static void adjustForOxygen(double atmoPressure, TreeSet<AtmosphericGases> atmosphericComposition) {

        Map<String, AtmosphericGases> atmoMap = atmosphericComposition
                .stream()
                .collect(Collectors.toMap(AtmosphericGases::getName, x -> x));

        int oxygenMax = Math.max(50,
                                 (int) (Dice._3d6() * 2 / atmoPressure)); //This could be a bit more involved and
        // interesting

        if (atmoMap.containsKey("CO2")) {
            if (atmoMap.get("CO2").getPercentageInAtmo() > oxygenMax) {
                AtmosphericGases co2 = atmoMap.get("CO2");
                atmoMap.remove("CO2");
                atmoMap.put("O2", AtmosphericGases.builder().withName("O2").withPercentageInAtmo(oxygenMax).build());
                //perhaps the remnant CO should be put in as N2 instead?
                atmoMap.put("CO2", AtmosphericGases.builder()
                                                   .withName("CO2")
                                                   .withPercentageInAtmo(co2.getPercentageInAtmo() - oxygenMax)
                                                   .build());

            } else {
                AtmosphericGases co2 = atmoMap.get("CO2");
                atmoMap.remove("CO2");
                atmoMap.put("O2",
                            AtmosphericGases.builder().withName("O2").withPercentageInAtmo(co2.getPercentageInAtmo())
                                            .build());
            }
        } else { //if no CO2 we just find the largest and take from that
            AtmosphericGases gas = atmosphericComposition.pollFirst();
            if (gas != null) {
                if (gas.getPercentageInAtmo() < oxygenMax) {
                    atmoMap.put("O2", AtmosphericGases.builder()
                                                      .withName("O2")
                                                      .withPercentageInAtmo(gas.getPercentageInAtmo())
                                                      .build());
                } else {
                    atmoMap.put("O2", AtmosphericGases.builder()
                                                      .withName("O2")
                                                      .withPercentageInAtmo(oxygenMax)
                                                      .build());
                    atmoMap.put(gas.getName(), AtmosphericGases.builder()
                                                               .withName("O2")
                                                               .withPercentageInAtmo(gas.getPercentageInAtmo() -
                                                                                     oxygenMax)
                                                               .build());
                }
            }
        }

        removeCombustibles(atmoMap);
        atmosphericComposition.clear();
        atmosphericComposition.addAll(atmoMap.values());
    }

    private static void removeCombustibles(Map<String, AtmosphericGases> atmoMap) {
        int removedpercentages = 0;
        if (atmoMap.containsKey("CH4")) {
            removedpercentages += atmoMap.get("CH4").getPercentageInAtmo();
            atmoMap.remove("CH4");
        }
        if (atmoMap.containsKey("H2")) {
            removedpercentages += atmoMap.get("H2").getPercentageInAtmo();
            atmoMap.remove("H2");
        }
        if (atmoMap.containsKey("NH3")) {
            removedpercentages += atmoMap.get("NH3").getPercentageInAtmo();
            atmoMap.remove("NH3");
        }
        if (removedpercentages > 0) {
            if (atmoMap.containsKey("N2")) {
                removedpercentages += atmoMap.get("N2").getPercentageInAtmo();
                atmoMap.remove("N2");
            }
            atmoMap.put("N2", AtmosphericGases.builder()
                                              .withName("N2")
                                              .withPercentageInAtmo(removedpercentages)
                                              .build());
        }
    }

    private static Breathing findLifeType(Set<AtmosphericGases> atmoshericComposition) {
        //TODO Allow for alternate gases such as Cl2
        return atmoshericComposition.stream()
                                    .map(AtmosphericGases::getName)
                                    .anyMatch(b -> b.equals("NH3"))
               ? Breathing.AMMONIA
               : Breathing.OXYGEN;


    }

    /**
     * /*TODO
     * great season variations from the orbital eccentricity and multiple stars system
     * day night variation estimation, is interesting for worlds with short year/long days
     */

    private static void setAllKindOfLocalTemperature(Planet planet, double atmoPressure, int hydrosphere,
                                                     double rotationalPeriod, double axialTilt, double surfaceTemp,
                                                     double orbitalPeriod) {

        double[][] temperatureRangeBand = new double[][]{ // First is Low Moderation atmos, then Average etc
                                                          {1.10, 1.07, 1.05, 1.03, 1.00, 0.97, 0.93, 0.87, 0.78, 0.68},
                                                          {1.05, 1.04, 1.03, 1.02, 1.00, 0.98, 0.95, 0.90, 0.82, 0.75},
                                                          {1.02, 1.02, 1.02, 1.01, 1.00, 0.99, 0.98, 0.95, 0.91, 0.87},
                                                          {1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00}
        };

        double[] summerTemperature = new double[10];
        double[] winterTemperature = new double[10];
        double[] latitudeTemperature = new double[10];
        double[] baseTemperature = new double[10];

        int testModeration = 0;
        testModeration += (hydrosphere - 60) / 10;
        testModeration += (atmoPressure < 0.1) ? -3 : 1;
        testModeration += (int) atmoPressure;
        testModeration += (rotationalPeriod < 10) ? -3 : 1;
        testModeration += (int) (Math.sqrt(rotationalPeriod / 24));
        testModeration += (int) (10 / axialTilt);

        String atmoModeration;
        if (atmoPressure == 0) atmoModeration = "No";
        else if (atmoPressure > 10) atmoModeration = "Extreme";
        else if (testModeration < -2) atmoModeration = "Low";
        else if (testModeration > 2) atmoModeration = "High";
        else atmoModeration = "Average";

        int atmoIndex;
        switch (atmoModeration) {
            case "High":
                atmoIndex = 2;
                break;
            case "Average":
                atmoIndex = 1;
                break;
            case "Extreme":
                atmoIndex = 3;
                break;
            default:
                atmoIndex = 0;
                break;
        }

        for (int i = 0; i < 10; i++) {
            latitudeTemperature[i] = temperatureRangeBand[atmoIndex][i] * surfaceTemp;
        }

        for (int i = 0; i < 10; i++) {
            baseTemperature[i] = latitudeTemperature[i] - 274;
        }

        for (int i = 0; i < 10; i++) {

            double seasonEffect = 1;
            // This part is supposed to shift the rangebands for summer /winter effects, it makes an
            // (to me unproven) assumption that winter temperatures at the poles is not changed by seasonal effects
            // this feels odd but I have to delve further into the science before I dismiss it.
            // the effect occurs from the intersection of axial tilt effects and rangeband effects in a way that
            //makes me suspect it is unintentional.
            int axialTiltEffect = (int) (axialTilt / 10);
            int summer = Math.max(0, i - axialTiltEffect);
            int winter = Math.min(9, i + axialTiltEffect);

            if (i < 3 && axialTiltEffect < 4) seasonEffect *= 0.75;
            if (i > 8 && axialTiltEffect > 3) seasonEffect *= 2;
            if (orbitalPeriod < 0.25 && !atmoModeration.equals("Low")) seasonEffect *= 0.75;
            if (orbitalPeriod > 3 && !atmoModeration.equals("High") && axialTilt > 40) seasonEffect *= 1.5;

            summerTemperature[i] = (int) (latitudeTemperature[summer] - latitudeTemperature[i]) * seasonEffect;
            winterTemperature[i] = (int) (latitudeTemperature[winter] - latitudeTemperature[i]) * seasonEffect;
        }

        planet.setRangeBandTemperature(DoubleStream.of(baseTemperature).mapToInt(t -> (int) Math.ceil(t)).toArray());
        planet.setRangeBandTempSummer(DoubleStream.of(summerTemperature).mapToInt(t -> (int) Math.ceil(t)).toArray());
        planet.setRangeBandTempWinter(DoubleStream.of(winterTemperature).mapToInt(t -> (int) Math.ceil(t)).toArray());

    }

    /*TODO
     * This should be reworked (in conjuction with atmo) to remove CL and F from naturally occuring and instead
     * treat them similar to Oxygen. Also the Ammonia is dependent on free water as written right now
     */
    private static boolean testLife(int baseTemperature,
                                    double atmoPressure,
                                    int hydrosphere,
                                    Set<AtmosphericGases> atmoshericComposition) {

        double lifeIndex = 0;

        if (baseTemperature < 100 || baseTemperature > 450) lifeIndex -= 5;
        else if (baseTemperature < 250 || baseTemperature > 350) lifeIndex -= 1;
        else lifeIndex += 3;

        if (atmoPressure < 0.1) lifeIndex -= 10;
        else if (atmoPressure > 5) lifeIndex -= 1;

        if (hydrosphere < 1) lifeIndex -= 3;
        else if (hydrosphere > 3) lifeIndex += 1;

        if (atmoshericComposition.stream().anyMatch(s -> s.getName().equals("NH3") && Dice.d6() < 3)) lifeIndex += 4;

        return lifeIndex > 0; //Nod to Gaia-theory, if there is any chance of life it will aways be life present
    }

    private static double findGreenhouseGases(Set<AtmosphericGases> atmoshericComposition, double atmoPressure) {
        double tempGreenhouseGasEffect = 0;

        for (AtmosphericGases gas : atmoshericComposition) {

            switch (gas.getName()) {
                case "CO2":
                    tempGreenhouseGasEffect += gas.getPercentageInAtmo() * atmoPressure;
                    break;
                case "CH4":
                    tempGreenhouseGasEffect += gas.getPercentageInAtmo() * atmoPressure * 4;
                    break;
                case "SO2":
                case "NH3":
                case "NO2":
                case "H2S":
                    tempGreenhouseGasEffect += gas.getPercentageInAtmo() * atmoPressure * 8;
                    break;
                case "H2SO4":
                    tempGreenhouseGasEffect += gas.getPercentageInAtmo() * atmoPressure * 16;
                    break;
                default:
                    tempGreenhouseGasEffect += gas.getPercentageInAtmo() * atmoPressure * 0;
                    break;
            }
        }
        return tempGreenhouseGasEffect;
    }


    private static double findAlbedo(boolean InnerZone,
                                     double atmoPressure,
                                     HydrosphereDescription hydrosphereDescription,
                                     Planet planet) {

        int mod = 0;
        int[] randAlbedoArray;
        Double[] albedoBase = new Double[]{0.75, 0.85, 0.95, 1.05, 1.15};

        if (InnerZone) {
            randAlbedoArray = new int[]{0, 2, 4, 7, 10};

            if (atmoPressure < 0.05) mod = 2;
            if (atmoPressure > 50) {
                mod = -4;
            } else if (atmoPressure > 5) {
                mod = -2;
            }
            if (planet.getHydrosphere() > 50 && hydrosphereDescription.equals(HydrosphereDescription.ICE_SHEET) &&
                mod > -2)
                mod = -2;
            if (planet.getHydrosphere() > 90 && hydrosphereDescription.equals(HydrosphereDescription.ICE_SHEET) &&
                mod > -4)
                mod = -4;

        } else {
            randAlbedoArray = new int[]{0, 4, 6, 8, 10};
            if (atmoPressure > 1) mod = 1;
        }
        return TableMaker.makeRoll(Dice._2d6() + mod, randAlbedoArray, albedoBase) + (Dice.d10() - 1) * 0.01;
    }

    private static int findTheHydrosphere(HydrosphereDescription hydrosphereDescription, int radius) {

        //        Supplier<Integer> zeroHydro = () -> 0;
        //        Supplier<Integer> superficialHydro = () -> Dice.d10()/2;
        //        Supplier<Integer> vLowHydro = Dice::d10;
        //        Supplier<Integer> lowHydro = () -> Dice.d10() + 10;
        //        Supplier<Integer> mediumHydro = () -> Dice.d20() + 20;
        //        Supplier<Integer> highHydro = () -> Dice.d20()+ Dice.d20()+ Dice.d20() +37;
        //        Supplier<Integer> vHighHydro = () -> 100;

        List<Supplier<Integer>> hydroList = Arrays.asList(() -> Dice.d10() / 2,
                                                          Dice::d10,
                                                          () -> Dice.d10() + 10,
                                                          () -> Dice.d20() + 20,
                                                          () -> Dice.d20() + Dice.d20() + Dice.d20() + 37,
                                                          () -> 100);
        int[] wetSmallPlanetHydro = {2, 5, 9, 10, 12, 13};
        int[] wetMediumPlanetHydro = {2, 4, 7, 9, 11, 12};
        int[] wetLargePlanetHydro = {0, 2, 3, 4, 7, 12};

        Integer tempHydro = 0;

        if (hydrosphereDescription.equals(HydrosphereDescription.LIQUID)
            || hydrosphereDescription.equals(HydrosphereDescription.ICE_SHEET)) {
            if (radius < 2000) {
                tempHydro = TableMaker.makeRoll(Dice._2d6(), wetSmallPlanetHydro, hydroList).get();
            } else if (radius < 4000) {
                tempHydro = TableMaker.makeRoll(Dice._2d6(), wetMediumPlanetHydro, hydroList).get();
            } else if (radius < 7000) {
                tempHydro = TableMaker.makeRoll(Dice._2d6(), wetLargePlanetHydro, hydroList).get();
            }
        } else if (hydrosphereDescription.equals(HydrosphereDescription.CRUSTAL)) tempHydro = 100;
        else if (hydrosphereDescription.equals(HydrosphereDescription.REMNANTS)) tempHydro = Dice.d6() / 2;

        tempHydro = Math.min(100, tempHydro);

        return tempHydro;
    }

    private static HydrosphereDescription findHydrosphereDescription(boolean InnerZone, int baseTemperature) {
        HydrosphereDescription tempHydroD;
        if (!InnerZone) {
            tempHydroD = HydrosphereDescription.CRUSTAL;
        } else if (baseTemperature > 500) {
            tempHydroD = HydrosphereDescription.NONE;
        } else if (baseTemperature > 370) {
            tempHydroD = HydrosphereDescription.VAPOR;
        } else if (baseTemperature > 245) {
            tempHydroD = HydrosphereDescription.LIQUID;
        } else {
            tempHydroD = HydrosphereDescription.ICE_SHEET;
        }
        return tempHydroD;
    }

    private static String findTectonicGroup(boolean InnerZone, double density) {

        String tempTectonics;
        if (InnerZone) {
            if (density < 0.7) {
                if (Dice.d6() < 4) {
                    tempTectonics = "Silicates core";
                } else {
                    tempTectonics = "Silicates, small metal core";
                }
            } else if (density < 1) {
                tempTectonics = "Iron-nickel, medium metal core";
            } else {
                tempTectonics = "Iron-nickel, large metal core";
            }
        } else {
            if (density < 0.3) {
                tempTectonics = "Ice core";
            } else if (density < 1) {
                tempTectonics = "Silicate core";
            } else {
                if (Dice.d6() < 4) {
                    tempTectonics = "Silicates core";
                } else {
                    tempTectonics = "Silicates, small metal core";
                }
            }
        }
        return tempTectonics;
    }
}
