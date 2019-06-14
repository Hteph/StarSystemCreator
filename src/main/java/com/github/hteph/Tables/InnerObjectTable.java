package com.github.hteph.Tables;

import java.util.TreeMap;

/**
 */
public class InnerObjectTable {

    private TreeMap<Integer, String> map = new TreeMap<>();

    public InnerObjectTable() {

        map.put(18, "J");
        map.put(17, "j");
        map.put(15, "E");
        map.put(14, "C");
        map.put(13, "T");
        map.put(8, "t");
        map.put(4, "A");
        map.put(2, "E");
    }

    public char findInnerObject(int roll){

        return map.get( map.floorKey(roll)).toCharArray()[0];

    }
}
