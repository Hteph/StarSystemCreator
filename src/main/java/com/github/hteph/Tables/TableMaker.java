package com.github.hteph.Tables;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableMaker {

    public static <E> E makeRoll(int roll, int[] numbers, E[] entries) {

        E result = null;
        if (numbers != null && numbers.length > 0 && entries != null && !(entries.length < numbers.length)) {
            TreeMap<Integer, E> map = new TreeMap<>( putIntoMap(numbers,entries));
            try {
                if (roll < map.firstKey()) roll = map.firstKey();
                result = map.get(map.floorKey(roll));
            } catch (Exception e) {
                result = entries[0];
            }
        }
        return result;
    }

    public static <E> E makeRoll(int roll, int[] numbers, List<E> entries) {

        E result = null;
        if (numbers != null && numbers.length > 0 && entries != null && !(entries.size() < numbers.length)) {
            TreeMap<Integer, E> map = new TreeMap<>(putIntoMap(numbers, entries));
            try {
                if (roll < map.firstKey()) roll = map.firstKey();
                result =  map.get(map.floorKey(roll));
            } catch (Exception e) {
                result =  entries.get(0);
            }
        }
        return result;
    }

    private static <E> Map<Integer, E> putIntoMap(int[] numbers, E[] array) {

        return IntStream.range(0, numbers.length)
                        .boxed()
                        .collect(Collectors.toMap(i -> numbers[i], i -> array[i]));
    }

    private static <E> Map<Integer, E> putIntoMap(int[] numbers, List<E> list) {

        return IntStream.range(0, numbers.length)
                        .boxed()
                        .collect(Collectors.toMap(i -> numbers[i], list::get));
    }
}

