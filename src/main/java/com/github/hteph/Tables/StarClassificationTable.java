package com.github.hteph.Tables;

import java.util.TreeMap;

/**

 */
public class StarClassificationTable {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(40000,"X");
        map.put(28000, "O");
        map.put(10000, "B");
        map.put(8000, "A");
        map.put(6000, "F");
        map.put(4900, "G");
        map.put(3500, "K");
        map.put(2000, "M");
        map.put(1300, "L");
        map.put(550, "T");
        map.put(0, "Y");
    }

    public static String findStarClass(int temperature){

        Integer baseTemp = map.floorKey(temperature);
        Integer topTemp = map.ceilingKey(temperature);
        System.out.println(baseTemp +" to "+ topTemp);
        int deciNumber =10 -(10*(temperature-baseTemp)/(map.ceilingKey(temperature)-baseTemp));
        return map.get(baseTemp)+ deciNumber;
    }

}
