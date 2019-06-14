package com.github.hteph.MainModules;

import java.io.IOException;
import java.util.ArrayList;
import com.github.hteph.Generators.GenerateStar;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class MainConnectorOfStuff {

	public static void main(String[] args) throws IOException {
		
		
		
		
		
			
		StellarObject testStar;

			testStar = GenerateStar.Generator();

		ArrayList<StellarObject> Test = StarSystemGenerator.Generator((Star) testStar);
		
		int i=0;
		
		while(i<Test.size()) {
		System.out.println(Test.get(i).toString());
		i++;
		}


	}

}
