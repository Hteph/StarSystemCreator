package test;

import com.github.hteph.Generators.GenerateAsteroidBelt;
import com.github.hteph.Generators.StarFactory;
import com.github.hteph.ObjectsOfAllSorts.AsteroidBelt;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {


		StellarObject star = StarFactory.generate("Test",'A',null);
		
		char[] orbitalObjectBasicList = {'A','t'};
		
		BigDecimal[] orbitalDistancesArray = {BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		
		AsteroidBelt belt = (AsteroidBelt) GenerateAsteroidBelt.Generator("Test", "Test", "Test", 1, (Star) star, orbitalObjectBasicList, orbitalDistancesArray);

		System.out.println(belt.toString());

	}

}
