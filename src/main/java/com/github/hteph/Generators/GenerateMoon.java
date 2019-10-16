package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.Utilities.Dice;

public class GenerateMoon {
    static void createMoons(Planet planet, boolean innerZone) {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        int number;
        if (!innerZone) {
            number = Math.max(0, (Dice._2d6() - 5) / 2);
        } else {
            number = (int) Math.max(0, (Dice._2d6() - 2) / 1.5);
        }

        if (number == 0) return; //If there is no moons, bail out of this

        double[] lunarOrbitDistance = new double[number];

        lunarOrbitDistance[0] = 3 + 150 / number * (Dice._2d6() - 2) / 10; //in planet Radii

        for (int i = 1; i < number; i++) lunarOrbitDistance[i] = lunarOrbitDistance[i - 1] + 1 + 150 / number * (Dice._2d6() - 2) / 10.0;


        for (int i = 0; i < number; i++) {

            Planet moon = (Planet) GenerateTerrestrialPlanet.Generator(planet.getArchiveID() + "." + i,
                                                                       planet.getName() + letters[i],
                                                                       "A moon",
                                                                       "lunar object",
                                                                       planet.getOrbitalDistance(),
                                                                       Dice.d6(2)?'m':'M',
                                                                       planet,
                                                                       lunarOrbitDistance[i]);

            // TODO here a roche limit check should be made!!

            planet.addLunarObjects(moon);
        }

    }
}