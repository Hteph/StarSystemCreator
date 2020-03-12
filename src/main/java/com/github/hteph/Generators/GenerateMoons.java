package com.github.hteph.Generators;

import com.codepoetics.protonpack.StreamUtils;
import com.github.hteph.ObjectsOfAllSorts.CentralRegistry;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.Utilities.Dice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class GenerateMoons {
    static List<String> createMoons(Planet planet, boolean innerZone) {

        int number;
        if (!innerZone) {
            number = (Dice._2d6() - 5) / 2;
        } else {
            number = (int) ((Dice._2d6() - 2) / 1.5);
        }

        if (number < 1) return null;

        double startDistance = 3 + 150 / (1.0 * number) * (Dice._2d6() - 2) / 10.0;
       return StreamUtils.zipWithIndex(DoubleStream
                                         .iterate(startDistance, d -> d + 1 + 150 / (1.0 * number) * (Dice._2d6() - 2) / 10.0)
                                         .limit(number)
                                         .boxed())
                   .map(s-> createMoon(planet, s.getValue(), s.getIndex()))
                         .map(CentralRegistry::putInArchive)
        .collect(Collectors.toList());


    }

    private static Planet createMoon(Planet planet, Double value, long index) {

        final int ASCII_STARTING_NUMBER = 97;
        final int ASCII_ENDING_NUMBER = 122;
        char asciiNumber;
        char asciiNumber2;
        String identifier;

        if (ASCII_STARTING_NUMBER+index > ASCII_ENDING_NUMBER) {
            asciiNumber = (char) (ASCII_STARTING_NUMBER + (int)(index/ASCII_STARTING_NUMBER));
            asciiNumber2 = (char) (ASCII_STARTING_NUMBER + (int)(index%ASCII_STARTING_NUMBER));
            identifier = "" + asciiNumber+ asciiNumber2;
        }else{
            identifier = "" + (char) (97+index);
        }

        // TODO here a roche limit check should be made!!
        return GenerateTerrestrialPlanet.generate(planet.getArchiveID() + "." + index,
                                                                  planet.getName()+"-" + identifier,
                                                                  "A moon",
                                                                  "lunar object",
                                                                  planet.getOrbitalDistance(),
                                                                  Dice.d6(2) ? 'm' : 'M',
                                                                  planet,
                                                                  value);

    }


}