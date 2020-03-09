package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.AsteroidBelt;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.TempOrbitalObject;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NumberUtilities;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.TreeSet;

import static com.github.hteph.Utilities.NumberUtilities.*;

public final class GenerateAsteroidBelt {


    private GenerateAsteroidBelt() {
        // No instances should be created by this method
    }


    public static OrbitalObjects generator(String archiveID, String name, String description, TempOrbitalObject tempObject, Star orbitingAround, TreeSet<TempOrbitalObject> tempOrbitalObjects) {

        boolean outerZone = false;

        AsteroidBelt belt = new AsteroidBelt(archiveID, name, description, BigDecimal.valueOf(tempObject.getOrbitDistance()), orbitingAround);
        double snowLine = 5 * Math.pow(orbitingAround.getLumosity().doubleValue(), 0.5);
        if (tempObject.getOrbitDistance() > snowLine) outerZone = true;

        belt.setEccentricity(((Dice.d6() - 1) * (Dice.d6() - 1)) / (100.0 * Dice.d6()));

        belt.setAsterioidBeltType(getBeltType(tempObject, orbitingAround, outerZone));

      //  belt.setMass((Dice.d10()) * Math.pow(10, 4 - getMassBase(orbitingAround, outerZone))); //TODO check this it look wrong to me, also not appropiate for Planetary rings

        if (tempObject.getOrbitObject() == 'j' || tempObject.getOrbitObject() == 'J') {
            belt.setObjectClass("Planetary Ring");
            belt.setSizeDistribution(0.001, 0.01);
            belt.setAsteroidBeltWidht(0.0);
        } else {
            belt.setObjectClass("Asteroid belt");
            belt.setAsteroidBeltWidht(getBeltWitdth(tempObject, tempOrbitalObjects));
            belt.setSizeDistribution(getMainAverageSize(), getLargestSizeAsteroids());
        }
        return belt;
    }

    private static double getMainAverageSize() {
        Double[] mainAverage = {0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.3, 0.5};
        int[] diceNumbers = {2, 3, 5, 7, 8, 10, 11, 12};
        return TableMaker.makeRoll(Dice._2d6(), diceNumbers, mainAverage);
    }

    private static Double getLargestSizeAsteroids() {
        Double[] maxSize = {1.0, 5.0, 10.0, 50.0, 100.0, 500.0};
        int[] dieNumbers = {1, 2, 3, 4, 5, 6};
        return TableMaker.makeRoll(Dice.d6(), dieNumbers, maxSize);
    }

    private static double getBeltWitdth(TempOrbitalObject tempObject, TreeSet<TempOrbitalObject> tempOrbitalObjects) {
        double beltWitdth;
        if (tempOrbitalObjects.lower(tempObject) != null && !tempOrbitalObjects.headSet(tempObject).isEmpty()) {
            beltWitdth = tempObject.getOrbitDistance() - tempOrbitalObjects.lower(tempObject).getOrbitDistance();
            if (tempOrbitalObjects.lower(tempObject).getOrbitObject() == 'j' || tempOrbitalObjects.lower(tempObject).getOrbitObject() == 'j')
                beltWitdth /= 2.0;
        } else beltWitdth = tempObject.getOrbitDistance() / 2.0;

        if (!tempOrbitalObjects.tailSet(tempObject).isEmpty()) {
            if (tempOrbitalObjects.higher(tempObject) != null
                        && tempOrbitalObjects.higher(tempObject).getOrbitObject() == 'j'
                        || tempOrbitalObjects.higher(tempObject).getOrbitObject() == 'J') {
                beltWitdth /= 2.0;
            }
        }
        return beltWitdth;
    }

    private static String getBeltType(TempOrbitalObject tempObject, Star orbitingAround, boolean outerZone) {
        double density;
        if (!outerZone) {
            double lum = orbitingAround.getLumosity().doubleValue();
            density = 0.3 + (Dice._2d6() - 2)
                                    * 0.127
                                    / Math.pow(0.4 + (tempObject.getOrbitDistance() / sqrt(lum)), 0.67);
        } else {
            density = 0.3 + (Dice._2d6() - 2) * 0.05;
        }


        String asterioidBeltType;// TODO the general type and composition of the belt can be further fleshed out
        //TODO make this to table maker
        int[] typeArray = new int[]{-2, -1, 6, 11};
        String[] asterioidBeltTypeArray = new String[]{"Metallic", "Silicate", "Carbonaceous", "Icy", "Icy"};
        double[] densityArray = new double[]{0, 0.8, 1, 1.2};

        int densMod = Arrays.binarySearch(densityArray, density);
        if (outerZone) densMod += 6;
        if (tempObject.getOrbitDistance() < 0.75 * Math.sqrt(orbitingAround.getLumosity().doubleValue())) densMod -= 2;

        int retVal = Arrays.binarySearch(typeArray, Dice._2d6() + densMod);

        if (retVal < 0) asterioidBeltType = asterioidBeltTypeArray[-retVal - 1];
        else asterioidBeltType = asterioidBeltTypeArray[retVal];
        return asterioidBeltType;
    }

    private static int getMassBase(Star orbitingAround, boolean outerZone) {
        int[] massArray = new int[]{0, 5, 7, 9, 11};
        Integer[] massBaseArray = new Integer[]{0, 5, 7, 9, 11};
        int massMod = orbitingAround.getAbundance();
        if (outerZone) massMod += 2;
        if (orbitingAround.getAge().doubleValue() > 7) massMod -= 1;
        //TODO +2 from multiple star system

        return TableMaker.makeRoll(Dice._2d6() + massMod, massArray, massBaseArray);
    }


}
