package com.github.hteph.Utilities.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class lunarObjectDistanceComparator implements Comparator<StellarObject>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(StellarObject o1, StellarObject o2) {
		
        if(o1.getOrbitalDistance().doubleValue() > o2.getOrbitalDistance().doubleValue()){
            return -1;
        } else {
            return 1;
        }
	}

}
