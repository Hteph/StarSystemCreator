package com.github.hteph.Tables;

import java.util.TreeMap;

public class OuterObjectTable {

    private TreeMap<Integer, String> map = new TreeMap<>();

    public OuterObjectTable() {

        map.put(18, "J");
        map.put(17, "C");
        map.put(15, "T");
        map.put(11, "t");
        map.put(8, "E");
        map.put(5, "j");
        map.put(4, "A");
        map.put(3, "c");
        map.put(2, "E");
    }


    public char findOuterObject(int roll){

        return map.get( map.floorKey(roll)).toCharArray()[0];

    }
}