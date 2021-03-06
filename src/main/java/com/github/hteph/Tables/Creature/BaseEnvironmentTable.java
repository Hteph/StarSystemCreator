package com.github.hteph.Tables.Creature;

import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.enums.EnvironmentalEnum;

import java.util.*;

import static com.github.hteph.Utilities.enums.EnvironmentalEnum.*;

public class BaseEnvironmentTable {

    private StellarObject planet;

    public BaseEnvironmentTable(StellarObject planet) {

        this.planet=planet;
    }

    public EnvironmentalEnum[] findBaseEnvironment(){

        if(planet instanceof Planet) return findTerrestialPlanetbaseEnvironment((Planet)planet);

        return new EnvironmentalEnum[]{EnvironmentalEnum.EXOTIC, EnvironmentalEnum.NONE};

    }

    private EnvironmentalEnum[] findTerrestialPlanetbaseEnvironment(Planet planet) {

        TreeMap<Integer, EnvironmentalEnum> map = new TreeMap<>();

        for (EnvironmentalEnum anEnum : EnvironmentalEnum.values()) {
            int chance = 10;
            if (anEnum.getClassification().contains("Z")) continue; // No civilisation base environments sophonts yet
            if (anEnum.getClassification().contains("H")
                    && planet.getHydrosphere() < 5) continue;
            if (anEnum.getClassification().contains("h")) chance += planet.getHydrosphere() / 10;
            if (anEnum.getClassification().contains("d")) chance -= planet.getHydrosphere() / 10;
            if (anEnum.getClassification().contains("c")) chance = (int) (chance / planet.getSurfaceTemp() / 274.0);
            if (anEnum.getClassification().contains("t")) chance = (int) (chance * planet.getSurfaceTemp() / 274.0);
            if (anEnum.getClassification().contains("u")) chance = (int) (chance * Math.random());
            //Add tectonics to chance of mountains?

            if(chance<1 ||anEnum==NONE) continue;

            if (map.isEmpty()) map.put(chance, anEnum);
            else {
                map.put(map.lastKey() + chance, anEnum);
            }
        }
//Take one out of the mix
        EnvironmentalEnum prim = map.remove(map.floorKey((int) (1 + Math.random() * map.lastKey())));

//If prim needs a sceondary terrain, draw one from those who remains.
// TODO Here a realism check should be added...Deep ocean and mountains frx.
        EnvironmentalEnum sec =EnvironmentalEnum.NONE;
        if(prim == EnvironmentalEnum.ALPINE){
            Set<EnvironmentalEnum> forbiddenList = new HashSet<>(Arrays.asList(
                    REEFS,
                    SHELVES,
                    DEEP_OCEAN,
                    MANAGED_AQUATIC,
                    CAVE,
                    EXOTIC,
                    BIOINDUSTRIAL,
                    GREENHOUSE,
                    TREE_CROP,
                    NONE,
                    FIELD_CROP,
                    MANAGED_GRASSLANDS));
            do {
                sec = map.get(map.floorKey((int) (1 + Math.random() * map.lastKey())));
            }while(forbiddenList.contains(sec));
        }else if(prim == RIVER_AND_STREAM
                || prim == LAKES
                || prim == COASTAL){
            Set<EnvironmentalEnum> forbiddenList = new HashSet<>(Arrays.asList(
                    TEMPERATE_AND_SEMI_DESERTS,
                    DESERT,
                    REEFS,
                    SHELVES,
                    DEEP_OCEAN,
                    MANAGED_AQUATIC,
                    CAVE,
                    NONE,
                    EXOTIC,
                    BIOINDUSTRIAL,
                    GREENHOUSE,
                    TREE_CROP,
                    FIELD_CROP,
                    MANAGED_GRASSLANDS));
            do {
                sec = map.get(map.floorKey((int) (1 + Math.random() * map.lastKey())));
            }while(forbiddenList.contains(sec));
        }
        return new EnvironmentalEnum[]{prim, sec};
    }
}