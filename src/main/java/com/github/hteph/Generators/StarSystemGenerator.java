package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.ObjectsOfAllSorts.TempOrbitalObject;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.RomanNumber;
import com.github.hteph.Utilities.comparators.tempOrbitalObjectComparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.github.hteph.Utilities.NumberUtilities.sqrt;
import static com.github.hteph.Utilities.NumberUtilities.squared;

public final class StarSystemGenerator {

    private StarSystemGenerator() {
    }

    //Methods ---------------------------------------------------------
    public static ArrayList<StellarObject> Generator(Star star) {

        final double INNER_LIMIT = Math.max(0.2 * star.getMass().doubleValue(), 0.0088 * sqrt(star.getLumosity().doubleValue()));
        //double innerHabitable = 0.95 * Math.pow(star.getLumosity(), 0.5); Try to use without locking to goldilock
        // theory
        //double outerHabitable = 1.3 * Math.pow(star.getLumosity(), 0.5);
        final double SNOW_LINE = 5 * sqrt(star.getLumosity().doubleValue());
        final double OUTER_LIMIT = 40 * star.getMass().doubleValue();

        ArrayList<StellarObject> starSystemList = new ArrayList<>();
        starSystemList.add(0, star);

        //TODO how many orbits? This code makes no sense! changed, but need revision
        final int NUMBER_OF_ORBITS = Dice._2d6() + (int) sqrt(star.getMass().doubleValue()) - 2;

//        BigDecimal[] orbitalDistancesArray = new BigDecimal[numberOfOrbits];
//
//        //set the orbit distances
//        orbitalDistancesArray[0] = BigDecimal.valueOf(0.05 * squared(star.getMass().doubleValue()) * (Dice._2d6()));
//
//        for (int i = 1; i < numberOfOrbits; i++) {
//
//            orbitalDistancesArray[i] = BigDecimal.valueOf((0.1 + orbitalDistancesArray[i - 1].doubleValue()
//                                                                             * (1.1 + (Dice.d10() * 0.1))));
//        }
        double startValue = 0.05 * squared(star.getMass().doubleValue()) * (Dice._2d6());
        List<TempOrbitalObject> orbitalDistances = DoubleStream.iterate(startValue, v -> 0.1 + v * 1.1 + (Dice.d10() * 0.1))
                                                               .limit(NUMBER_OF_ORBITS)
                                                               .boxed()
                                                               .map(TempOrbitalObject::new)
                                                               .collect(Collectors.toList());

        TreeSet<TempOrbitalObject> tempOrbitalObjects = new TreeSet<>(new tempOrbitalObjectComparator());
        tempOrbitalObjects.addAll(orbitalDistances);
        if (Dice.d6(6)) {
            Optional.ofNullable(tempOrbitalObjects.ceiling(new TempOrbitalObject(SNOW_LINE)))
                    .ifPresent(s -> setDominantGasGiant(s, tempOrbitalObjects));
        }
        tempOrbitalObjects.forEach(s -> setGeneralOrbitContent(INNER_LIMIT, SNOW_LINE, OUTER_LIMIT, s));

//        //populating the orbits, empty at start
//        char[] orbitalObjectBasicList = new char[orbitalDistancesArray.length];
//        for (int i = 0; i < numberOfOrbits; i++) orbitalObjectBasicList[i] = '-';

        //Dominant Gas giant (with accompanying Asteroid belt)?

//        if (Dice.d6(6)) {
//            for (int i = numberOfOrbits - 2; i > 0; i--) {
//                if (orbitalDistancesArray[i].doubleValue() < snowLine) {
//                    orbitalObjectBasicList[i + 1] = 'J';
//                    if (Dice.d6(6)) {
//                        orbitalObjectBasicList[i] = 'A';
//                        astroidBeltCounter++;
//                    }
//                    break;
//                }
//            }
//        }

        //General orbit content

//        for (int i = 0; i < NUMBER_OF_ORBITS; i++) {
//            getGeneralOrbitContent(INNER_LIMIT, SNOW_LINE, OUTER_LIMIT, orbitalDistancesArray, orbitalObjectBasicList, i);
//        }

        //Detailed bodies
        int objectCounter = 1;
        int astroidBeltCounter = 1;
        for (TempOrbitalObject tempObject :tempOrbitalObjects) {

            String numeral = RomanNumber.toRoman(objectCounter);
            String classificationName;
            String description;
            switch (tempObject.getOrbitObject()) {
                case 'j':
                    classificationName = "Gas Giant";
                    description = " A relatively small Gas Giant";
                    star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID() + "." + numeral,
                                                                     star.getName() + " " + numeral,
                                                                     description,
                                                                     classificationName,
                                                                     BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                     'j',
                                                                     star));
                    objectCounter++;
                    break;
                case 'J':
                    classificationName = "Super Jovian";
                    description = "A truly massive Gas Giant, dominating the whole system";
                    star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID() + "." + numeral,
                                                                     star.getName() + " " + numeral,
                                                                     description,
                                                                     classificationName,
                                                                     BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                     'J',
                                                                     star));
                    objectCounter++;
                    break;
                case 't':
                    classificationName = "Planetoid";
                    description = "Small and nicely rounded";
                    star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                               star.getName() + " " + numeral,
                                                                               description, classificationName,
                                                                               BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                               't',
                                                                               star,
                                                                               0));
                    objectCounter++;
                    break;
                case 'T':
                    classificationName = "Terrestial";
                    description = "Large and round";
                    star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                               star.getName() + " " + numeral,
                                                                               description, classificationName,
                                                                               BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                               'T',
                                                                               star,
                                                                               0));
                    objectCounter++;
                    break;
                case 'C':
                    classificationName = "Catched Terrestial";
                    description = "Large and round, but from not originated in this system";
                    star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                               star.getName() + " " + numeral,
                                                                               description, classificationName,
                                                                               BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                               'C',
                                                                               star,
                                                                               0));
                    objectCounter++;
                    break;
                case 'c': //TODO this should use a special generator to allow for strange stuff as hulks, ancient
                    // stations etc etc
                    description = "Smaller than a planet, but not one of those asteroids, and not from here to start with";
                    star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                               star.getName() + " " + numeral,
                                                                               description,
                                                                               "Catched object",
                                                                               BigDecimal.valueOf(tempObject.getOrbitDistance()),
                                                                               'c',
                                                                               star,
                                                                               0));
                    objectCounter++;
                    break;
                case 'A':

                    star.addOrbitalObjects(GenerateAsteroidBelt.generator(star.getArchiveID() + ".A" + objectCounter,
                                                                          "Asterioidbelt " + astroidBeltCounter,
                                                                          "A bunch of blocks",
                                                                          tempObject,
                                                                          star,
                                                                          tempOrbitalObjects));
                    astroidBeltCounter++;
                    break;

                default:
                    //Do nothing (probably 'E')
                    break;
            }
        }
        return starSystemList;
    }

    private static void setGeneralOrbitContent(double innerLimit, double snowLine, double outerLimit, TempOrbitalObject tempOrbitalObject) {
        int[] outerNumbersList = {2, 3, 4, 5, 8, 11, 15, 17, 18};
        Character[] outerObjectList = {'E', 'c', 'A', 'j', 'E', 't', 'T', 'C', 'J'};
        int[] innerNumbersList = {2, 4, 8, 13, 14, 15, 17, 18};
        Character[] innerObjectList = {'E', 'A', 't', 'T', 'C', 'E', 'j', 'J'};

        if (tempOrbitalObject.getOrbitDistance() > outerLimit || tempOrbitalObject.getOrbitDistance() < innerLimit) {
            tempOrbitalObject.setOrbitObject('E'); //empty orbit because of distance from Star

        } else if (tempOrbitalObject.getOrbitDistance() < snowLine) {
            if (tempOrbitalObject.getOrbitObject() == '-') tempOrbitalObject.setOrbitObject(TableMaker.makeRoll(Dice._3d6(),
                                                                                                                innerNumbersList,
                                                                                                                innerObjectList));
        } else {
            if (tempOrbitalObject.getOrbitObject() == '-') tempOrbitalObject.setOrbitObject(TableMaker.makeRoll(Dice._3d6(),
                                                                                                                outerNumbersList,
                                                                                                                outerObjectList));
        }

    }

    private static void setDominantGasGiant(TempOrbitalObject dominantGasGiant, TreeSet<TempOrbitalObject> tempOrbitalObjects) {

        dominantGasGiant.setOrbitObject('J');
        if (Dice.d6(6)) {
            Optional.ofNullable(tempOrbitalObjects.ceiling(dominantGasGiant))
                    .ifPresent(s -> s.setOrbitObject('A'));
        }

    }

    private static void getGeneralOrbitContent(double innerLimit, double snowLine, double outerLimit, BigDecimal[] orbitalDistancesArray, char[] orbitalObjectBasicList, int i) {

        int[] outerNumbersList = {2, 3, 4, 5, 8, 11, 15, 17, 18};
        Character[] outerObjectList = {'E', 'c', 'A', 'j', 'E', 't', 'T', 'C', 'J'};
        int[] innerNumbersList = {2, 4, 8, 13, 14, 15, 17, 18};
        Character[] innerObjectList = {'E', 'A', 't', 'T', 'C', 'E', 'j', 'J'};

        if (orbitalDistancesArray[i].doubleValue() > outerLimit || orbitalDistancesArray[i].doubleValue() <
                                                                           innerLimit) {
            // Do nothing
            orbitalObjectBasicList[i] = '+';

        } else if (orbitalDistancesArray[i].doubleValue() < snowLine) {
            if (orbitalObjectBasicList[i] == '-') orbitalObjectBasicList[i] = TableMaker.makeRoll(Dice._3d6(),
                                                                                                  innerNumbersList,
                                                                                                  innerObjectList);
        } else {
            if (orbitalObjectBasicList[i] == '-') orbitalObjectBasicList[i] = TableMaker.makeRoll(Dice._3d6(),
                                                                                                  outerNumbersList,
                                                                                                  outerObjectList);
        }
    }

    private static OrbitalObjects getActualOrbitObject(Star star, TempOrbitalObject tempOrbitalObject) {
        OrbitalObjects orbitalObject = null;


        String numeral = RomanNumber.toRoman(objectCounter);
        String classificationName;
        String description;
        switch (tempOrbitalObject.getOrbitObject()) {
            case 'j':
                classificationName = "Gas Giant";
                description = " A relatively small Gas Giant";
                star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID() + "." + numeral,
                                                                 star.getName() + " " + numeral,
                                                                 description,
                                                                 classificationName,
                                                                 orbitalDistancesArray[i],
                                                                 orbitalObjectBasicList[i],
                                                                 star));
                objectCounter++;
                break;
            case 'J':
                classificationName = "Super Jovian";
                description = "A truly massive Gas Giant, dominating the whole system";
                star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID() + "." + numeral,
                                                                 star.getName() + " " + numeral,
                                                                 description,
                                                                 classificationName,
                                                                 orbitalDistancesArray[i],
                                                                 orbitalObjectBasicList[i],
                                                                 star));
                objectCounter++;
                break;
            case 't':
                classificationName = "Planetoid";
                description = "Small and nicely rounded";
                star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                           star.getName() + " " + numeral,
                                                                           description, classificationName,
                                                                           orbitalDistancesArray[i],
                                                                           orbitalObjectBasicList[i],
                                                                           star,
                                                                           0));
                objectCounter++;
                break;
            case 'T':
                classificationName = "Terrestial";
                description = "Large and round";
                star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                           star.getName() + " " + numeral,
                                                                           description, classificationName,
                                                                           orbitalDistancesArray[i],
                                                                           orbitalObjectBasicList[i],
                                                                           star,
                                                                           0));
                objectCounter++;
                break;
            case 'C':
                classificationName = "Catched Terrestial";
                description = "Large and round, but from not originated in this system";
                star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                           star.getName() + " " + numeral,
                                                                           description, classificationName,
                                                                           orbitalDistancesArray[i],
                                                                           orbitalObjectBasicList[i],
                                                                           star,
                                                                           0));
                objectCounter++;
                break;
            case 'c': //TODO this should use a special generator to allow for strange stuff as hulks, ancient
                // stations etc etc
                description = "Smaller than a planet, but not one of those asteroids, and not from here to start with";
                star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID() + "." + numeral,
                                                                           star.getName() + " " + numeral,
                                                                           description,
                                                                           "Catched object",
                                                                           orbitalDistancesArray[i],
                                                                           orbitalObjectBasicList[i],
                                                                           star,
                                                                           0));
                objectCounter++;
                break;
            case 'A':
                star.getOrbitalObjects().stream().filter(s -> s.)
                star.addOrbitalObjects(GenerateAsteroidBelt.generator(star.getArchiveID() + ".A" + i,
                                                                      "Asterioidbelt " + astroidBeltCounter,
                                                                      "A bunch of blocks",
                                                                      i,
                                                                      star,
                                                                      orbitalObjectBasicList,
                                                                      orbitalDistancesArray));
                astroidBeltCounter++;
                break;

            default:
                //Do nothing (probably 'E')
                break;
        }

        return orbitalObject;
    }
}
