package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.AsteroidBelt;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;

import java.math.BigDecimal;
import java.util.Arrays;

public final class GenerateAsteroidBelt {


    private GenerateAsteroidBelt() {
        // No instances should be created by this method
    }


    public static OrbitalObjects Generator(String archiveID, String name, String description, int orbitNumber, Star orbitingAround, char[] orbitalObjectBasicList, BigDecimal[] orbitalDistancesArray) {

        double eccentricity;
        double density;
        String asterioidBeltType;
        boolean outerZone = false;

        AsteroidBelt belt = new AsteroidBelt(archiveID, name, description, orbitalDistancesArray[orbitNumber], orbitingAround);
        double snowLine = 5 * Math.pow(orbitingAround.getLumosity().doubleValue(), 0.5);
        if (orbitalDistancesArray[orbitNumber].doubleValue() > snowLine) outerZone = true;

        //Eccentricity
        eccentricity = ((Dice.d6() - 1) * (Dice.d6() - 1) )/ (100 * Dice.d6());
        belt.setEccentricity(eccentricity);

        if (!outerZone) {
            density = 0.3 + (Dice._2d6() - 2) * 0.127 / Math.pow(
                    0.4 + (orbitalDistancesArray[orbitNumber].doubleValue() / Math.pow(orbitingAround.getLumosity().doubleValue(), 0.5)), 0.67);
        } else {
            density = 0.3 + (Dice._2d6() - 2) * 0.05;
        }

        // TODO the general type and composition of the belt can be further fleshed out
        int[] typeArray = new int[]{-2, -1, 6, 11};
        String[] asterioidBeltTypeArray = new String[]{"Metallic", "Silicate", "Carbonaceous", "Icy", "Icy"};
        double[] densityArray = new double[]{0, 0.8, 1, 1.2};

        int densMod = Arrays.binarySearch(densityArray, density);
        if (outerZone) densMod += 6;
        if (orbitalDistancesArray[orbitNumber].doubleValue() < 0.75 * Math.sqrt(orbitingAround.getLumosity().doubleValue())) densMod -= 2;

        int retVal = Arrays.binarySearch(typeArray, Dice._2d6() + densMod);

        if (retVal < 0) asterioidBeltType = asterioidBeltTypeArray[-retVal - 1];
        else asterioidBeltType = asterioidBeltTypeArray[retVal];

        belt.setAsterioidBeltType(asterioidBeltType);

        int[] massArray = new int[]{0, 5, 7, 9, 11};
        Integer[] massBaseArray = new Integer[]{0, 5, 7, 9, 11};
        int massMod = orbitingAround.getAbundance();
        if (outerZone) massMod += 2;
        if (orbitingAround.getAge().doubleValue() > 7) massMod -= 1;
        //TODO +2 from multiple star system

        int massBase = TableMaker.makeRoll(Dice._2d6()+massMod,massArray,massBaseArray);
        belt.setMass((Dice.d10()) * Math.pow(10, 4 - massBase));

        double beltWitdth = 0;
        if (orbitalObjectBasicList[orbitNumber] == 'j' || orbitalObjectBasicList[orbitNumber] == 'J') {
            belt.setObjectClass("Planetary Ring");
            belt.setSizeDistribution(0.001, 0.01);
        } else {
            belt.setObjectClass("Asteroid belt");
            if (orbitNumber > 0) beltWitdth = orbitalDistancesArray[orbitNumber].doubleValue() - orbitalDistancesArray[orbitNumber - 1].doubleValue();
            else beltWitdth = orbitalDistancesArray[orbitNumber].doubleValue() / 2.0;
            if (orbitNumber > 0) {
                if (orbitalObjectBasicList[orbitNumber - 1] == 'j' || orbitalObjectBasicList[orbitNumber - 1] == 'J') beltWitdth /= 2.0;
            }
            if (orbitNumber < orbitalObjectBasicList.length - 1) {
                if (orbitalObjectBasicList[orbitNumber + 1] == 'j' || orbitalObjectBasicList[orbitNumber + 1] == 'J') beltWitdth /= 2.0;
            }

            //Average Asteroid size distribution
            Double[] mainAverage = {0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.3, 0.5};
            int[] diceNumbers = {2, 3, 5, 7, 8, 10, 11, 12};

            //Average large sized Asteroid
            Double[] maxSize = {1.0, 5.0, 10.0, 50.0, 100.0, 500.0};
            int[] dieNumbers = {1, 2, 3, 4, 5, 6};


            belt.setSizeDistribution(TableMaker.makeRoll(Dice._2d6(),diceNumbers,mainAverage),
                                     TableMaker.makeRoll(Dice.d6(),dieNumbers,maxSize));
        }

        belt.setAsteroidBeltWidht(beltWitdth);
        return belt;
    }
    // Inner methods -------------------------------------------------------------------------------------------------


}
