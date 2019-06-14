package com.github.hteph.Tables;

import java.util.TreeMap;


public class TableMaker {

    public <E> E makeRoll(int roll, int[] numbers, E[] entries){

        TreeMap<Integer, E> map = new TreeMap<>();

        for(int i=0;i<numbers.length;i++) map.put(numbers[i],entries[i]);

        if(roll<map.firstKey())roll=map.firstKey();
        return map.get( map.floorKey(roll));

    }
}

