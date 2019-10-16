package com.github.hteph.Generators;

import java.math.BigDecimal;

import com.github.hteph.ObjectsOfAllSorts.*;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;

public final class JovianGenerator {

	// Constructor ----------------------------------------------	
	private JovianGenerator() {
		//No instances of this, thank you
	}

	//Methods --------------------------------------------------

	static OrbitalObjects Generator(String archiveID,
	                                String name,
									String description,
									String classificationName,
									BigDecimal orbitDistance,
									char orbitalObjectClass,
									Star orbitingAround) {

		double mass;
		int radius;
		double density;
		double orbitalPeriod; //in earth years
		double axialTilt;
		double eccentricity;
		double rotationalPeriod;
		double magneticField;
		int baseTemperature;
		boolean InnerZone = false;
		double orbitalInclination;

		//String lifeType; // TODO allow for Jovian life in the future


		Jovian gasGiant = new Jovian (archiveID, name, description, classificationName, orbitDistance, orbitingAround);

		double snowLine = 5 * Math.pow(orbitingAround.getLumosity().doubleValue(), 0.5);
		if(orbitDistance.doubleValue()<snowLine) InnerZone=true;

// size may not be all, but here it is set

		if(orbitalObjectClass=='J') {
			mass = 250*Dice._3d6()+Dice.d10()*100;
			radius = (int) (60000+(Dice.d10()-orbitingAround.getAge().doubleValue()/2.0)*2000);
			gasGiant.setRadius(radius);
			gasGiant.setMass(mass);
		}
		else {
			radius = (Dice._2d6())*7000;
			if(InnerZone) density = 0.1*Dice.d10()*0.025;
			else density = 0.08*Dice.d10()*0.025;
			mass = (int) Math.pow(radius/6380,3)*density;
			gasGiant.setRadius(radius);
			gasGiant.setMass(mass);
		}

		orbitalPeriod = Math.pow(Math.pow(orbitDistance.doubleValue(),3)/orbitingAround.getMass().doubleValue(),0.5); //in earth years
		gasGiant.setOrbitalPeriod(orbitalPeriod);

//Eccentricity and Inclination

		int eccentryMod=1;

		eccentricity=eccentryMod*(Dice.d6()-1)*(Dice.d6()-1)/(100*Dice.d6());
		axialTilt = (Dice.d6()-1)+(Dice.d6()-1)/Dice.d6();
		orbitalInclination = eccentryMod*(Dice.d6()+Dice.d6())/(1+mass/10);	

		gasGiant.setEccentricity(eccentricity);
		gasGiant.setAxialTilt(axialTilt);
		gasGiant.setOrbitalInclination(orbitalInclination);

//Rotational Period
		double tidalForce = orbitingAround.getMass().doubleValue()*26640000/Math.pow(orbitDistance.doubleValue()*400,3);
		rotationalPeriod = (Dice.d6()+Dice.d6()+8)*(1+0.1*(tidalForce*orbitingAround.getAge().doubleValue()-Math.pow(mass, 0.5)));
		if(Dice.d6()<2) rotationalPeriod=Math.pow(rotationalPeriod,Dice.d6());

		gasGiant.setRotationalPeriod(rotationalPeriod);
//Magnetic field
		int[] magneticMassArray = {0,50,200,500};
		Double[] magneticMassArrayMin = {0.1 , 0.25 , 0.5 , 1.5, 1.5 };
		Double[] magneticMassArrayMax = {1d , 1.5 , 3d , 25d, 25d };

		magneticField = (TableMaker.makeRoll((int) mass,magneticMassArray,magneticMassArrayMax)
								 - TableMaker.makeRoll((int) mass,magneticMassArray,magneticMassArrayMin))
								/10
								*Dice.d10();
		gasGiant.setMagneticField(magneticField);
//Temperature
		baseTemperature = (int) (255/Math.sqrt((orbitDistance.doubleValue()/Math.sqrt(orbitingAround.getLumosity().doubleValue()))));
		gasGiant.setBaseTemperature(baseTemperature);

		return gasGiant;
	}
}
