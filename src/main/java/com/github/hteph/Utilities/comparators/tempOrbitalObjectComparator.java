package com.github.hteph.Utilities.comparators;

import com.github.hteph.ObjectsOfAllSorts.TempOrbitalObject;

import java.io.Serializable;
import java.util.Comparator;

public class tempOrbitalObjectComparator implements Comparator<TempOrbitalObject>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(TempOrbitalObject o1, TempOrbitalObject o2) {
		
        if(o1.getOrbitDistance()> o2.getOrbitDistance()){
            return -1;
        } else {
            return 1;
        }
	}

}
