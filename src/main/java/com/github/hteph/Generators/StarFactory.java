package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Tables.StarClassificationTable;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NameGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

public final class StarFactory {

	private StarFactory() {
		//No instances should be created of this method
	}

	//Method --------------------------------------------------------------------
	public static  StellarObject generate(String systemName, char systemPosition, Star star) {

		NameGenerator randomNameGenerator = null;
        try {
            randomNameGenerator = new NameGenerator("RomanNames");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       double mass = generateMass();
		double diameter= Math.pow(mass,2/3.0);
		int temperature = 100*(int)((Math.round((500+4800*Math.pow(mass, 0.5)))*(0.8+Math.random()*0.4))/100);
		double lumosity = Math.pow(mass, 3.5); //Solar relative units
        String starClass = StarClassificationTable.findStarClass(temperature);
		double maxAge =10*Math.pow(1/mass, 2.5);
		double age = (0.3+Math.random()*0.6)*Math.min(maxAge,13);// in billion of earth years
		double halfAgeBalance=2*age/maxAge;
		lumosity *= Math.pow(halfAgeBalance, 0.5);
		diameter *= Math.pow(halfAgeBalance, 1/3.0);

		//TODO abundance should be done nicer!
        int abundance = generateAbundance(age);

		String description=" A star of "+starClass+" type";

        //TODO allow for multiple Starsystems, ie archiveID not hardcoded


        String starName = null;
        try {
            starName = randomNameGenerator.compose((int)(2+ Dice.d6()/2));
        } catch (Exception e) {
            starName = "Unknown";
        }


        return Star.builder()
            .withArchiveID(systemName+" "+ Character.toString(systemPosition))
            .withName(starName)
            .withDescription(description)
            .withLumosity(lumosity)
            .withMass(mass)
            .withDiameter(diameter)
            .withClassification(starClass)
            .withAge(abundance)
            .withAbundance(abundance)
            .build();
	}

    private static int generateAbundance(double age) {
        int abundance;
        int[] abundanceArray = new int[] {0,10,13,19,22};
        int retVal = Arrays.binarySearch(abundanceArray, (int) (Dice._2d6()+age));
        if(retVal<0) abundance = 2-retVal+1;
        else abundance = 2-retVal;
        return abundance;
    }

    private static double generateMass() {
        double mass;
        int testDice =Dice._3d6()-3;
        double randN =testDice/(15.0+Math.random()/10); //turning the dice roll into a continous sligthly skewed randomnumber.
        mass = 0.045/(0.001+Math.pow(randN,5)); // <-----------------------------------------MOST IMPORTANT STARTING POINT
        return mass;
    }
}

