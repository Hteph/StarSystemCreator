package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Tables.InnerObjectTable;
import com.github.hteph.Tables.OuterObjectTable;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.RomanNumber;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class StarSystemGenerator  {

	private StarSystemGenerator() {	}

	//Methods ---------------------------------------------------------
	public static ArrayList<StellarObject> Generator(Star star){

		OuterObjectTable outerTable = new OuterObjectTable();
		InnerObjectTable innerTable = new InnerObjectTable();
		
		double innerLimit = Math.max(0.2 * star.getMass().doubleValue() , 0.0088 * Math.pow(star.getLumosity().doubleValue(), 0.5));
		//double innerHabitable = 0.95 * Math.pow(star.getLumosity(), 0.5); Try to use without locking to goldilock theory
		//double outerHabitable = 1.3 * Math.pow(star.getLumosity(), 0.5);
		double snowLine = 5 * Math.pow(star.getLumosity().doubleValue(), 0.5);
		double outerLimit = 40 * star.getMass().doubleValue();
		ArrayList<StellarObject> starSystemList = new ArrayList<>();

		starSystemList.add(0,star);

		//TODO how many orbits? This code makes no sense!
		int numberOfOrbits = Dice._2d6() + (int)Math.sqrt(star.getMass().doubleValue())-2;

		BigDecimal[] orbitalDistancesArray = new BigDecimal[numberOfOrbits];
			
		//set the orbit distances
		orbitalDistancesArray[0] = BigDecimal.valueOf(0.05*Math.pow(star.getMass().doubleValue(), 2)*(Dice.d6()+Dice.d6()));
		
		for(int i=1;i<numberOfOrbits;i++) {

				orbitalDistancesArray[i] = BigDecimal.valueOf((0.1+orbitalDistancesArray[i-1].doubleValue()*(1.1+(Dice.d10()*0.1))));
		}

		//populating the orbits, empty at start
		char[] orbitalObjectBasicList = new char[orbitalDistancesArray.length];		
		for(int i=0;i<numberOfOrbits;i++) orbitalObjectBasicList[i]='-';

		//Dominant Gas giant (with accompanying Asteroid belt)?
        int astroidBeltCounter=0;
		if(Dice.d6()<6){
			for(int i=numberOfOrbits-2;i>0;i--) {
				if(orbitalDistancesArray[i].doubleValue()<snowLine){
					orbitalObjectBasicList[i+1]='J';
					if(Dice.d6()<6) {
                        orbitalObjectBasicList[i]='A';
                        astroidBeltCounter++;
                    }
					break;
				}
			}
		}

		//General orbit content
		for(int i=0;i<numberOfOrbits;i++) {

			if(orbitalDistancesArray[i].doubleValue()>outerLimit || orbitalDistancesArray[i].doubleValue()<innerLimit) {
				// Do nothing
				orbitalObjectBasicList[i]='+';
				
			}else if(orbitalDistancesArray[i].doubleValue()<snowLine){
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]=innerTable.findInnerObject(Dice._3d6test());
			}else{
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]= outerTable.findOuterObject(Dice._3d6test());
			}
		}
		
		//Detailed bodies
		int objectCounter=1;
		for(int i=0;i<numberOfOrbits;i++){

			String numeral = RomanNumber.toRoman(objectCounter);
			String classificationName;
			String description;
			switch (orbitalObjectBasicList[i]) {
				case 'j':
					classificationName = "Gas Giant";
					description = " A relatively small Gas Giant";
					star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID()+"."+numeral,
																	 star.getName()+" "+numeral,
																	 description,
																	 classificationName,
																	 orbitalDistancesArray[i],
																	 orbitalObjectBasicList[i],
																	 star));
					objectCounter++;
					break;
				case 'J':
					classificationName = "Super Jovian";
					description = "A truly massive Gas Giant, dominating the whole system";
					star.addOrbitalObjects(JovianGenerator.Generator(star.getArchiveID()+"."+numeral,
																	 star.getName()+" "+numeral,
																	 description,
																	 classificationName,
																	 orbitalDistancesArray[i],
																	 orbitalObjectBasicList[i],
																	 star));
					objectCounter++;
					break;
				case 't':
					classificationName = "Planetoid";
					description = "Small and nicely rounded";
					star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID()+"."+numeral,
																			   star.getName()+" "+numeral,
																			   description, classificationName,
																			   orbitalDistancesArray[i],
																			   orbitalObjectBasicList[i],
																			   star,
																			   0));
					objectCounter++;
					break;
				case 'T':
					classificationName = "Terrestial";
					description = "Large and round";
					star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID()+"."+numeral,
																			   star.getName()+" "+numeral,
																			   description, classificationName,
																			   orbitalDistancesArray[i],
																			   orbitalObjectBasicList[i],
																			   star,
																			   0));
					objectCounter++;
					break;
				case 'C':
					classificationName = "Catched Terrestial";
					description = "Large and round, but from not originated in this system";
					star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID()+"."+numeral,
																			   star.getName()+" "+numeral,
																			   description, classificationName,
																			   orbitalDistancesArray[i],
																			   orbitalObjectBasicList[i],
																			   star,
																			   0));
					objectCounter++;
					break;
				case 'c': //TODO this should use a special generator to allow for strange stuff as hulks, ancient stations etc etc
					star.addOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getArchiveID()+"."+numeral,
																			   star.getName()+" " +numeral,
																			   "Smaller than a planet, but not one of those asteroids, and not from here to start with",
																			   "Catched object",
																			   orbitalDistancesArray[i],
																			   orbitalObjectBasicList[i],
																			   star,
																			   0));
					objectCounter++;
					break;
				case 'A':
					star.addOrbitalObjects(GenerateAsteroidBelt.Generator(star.getArchiveID()+".A"+i,
																		  "Asterioidbelt "+astroidBeltCounter,
																		  "A bunch of blocks",
																		  i,
																		  star,
																		  orbitalObjectBasicList,
																		  orbitalDistancesArray));
					astroidBeltCounter++;
					break;

				default:
					//Do nothing (probably 'E')
					break;
			}
		}
		return starSystemList;
	}

}