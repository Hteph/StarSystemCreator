package com.github.hteph.Generators;

import java.math.BigDecimal;

import com.github.hteph.ObjectsOfAllSorts.*;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NumberUtilities;

import static com.github.hteph.Utilities.NumberUtilities.*;

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

		//String lifeType; // TODO allow for Jovian life in the future


		Jovian gasGiant = new Jovian (archiveID, name, description, classificationName, orbitDistance, orbitingAround);

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
			gasGiant.setMass((int) cubed(gasGiant.getRadius()/6380.0)*density);
		}
        gasGiant.setOrbitalPeriod(sqrt(cubed(orbitDistance.doubleValue())/orbitingAround.getMass().doubleValue())); //in earth years

//Eccentricity and Inclination

		int eccentryMod=1; //TODO This should probably be changed for the smaller Jovians

        gasGiant.setEccentricity(eccentryMod*(Dice._2d6()-2)/(100.0*Dice.d6()));
        gasGiant.setAxialTilt( (Dice._2d6()-2)/(1.0*Dice.d6())); //TODO this should be expanded
        gasGiant.setOrbitalInclination(eccentryMod*(Dice._2d6())/(1+gasGiant.getMass()/10.0));

//Rotational Period
		double tidalForce = orbitingAround.getMass().doubleValue()*26640000 / cubed(orbitDistance.doubleValue() * 400);
		double rotationalPeriod = (Dice._2d6()+8)
                           *(1+0.1*(tidalForce*orbitingAround.getAge().doubleValue()-sqrt(gasGiant.getMass())));
		if(Dice.d6()<2) rotationalPeriod=Math.pow(rotationalPeriod,Dice.d6());
		gasGiant.setRotationalPeriod(rotationalPeriod);

//Magnetic field
		int[] magneticMassArray = {0,50,200,500};
		Double[] magneticMassArrayMin = {0.1 , 0.25 , 0.5 , 1.5, 1.5 };
		Double[] magneticMassArrayMax = {1d , 1.5 , 3d , 25d, 25d };

        gasGiant.setMagneticField((TableMaker.makeRoll((int) gasGiant.getMass(),magneticMassArray,magneticMassArrayMax)
								 - TableMaker.makeRoll((int) gasGiant.getMass(),magneticMassArray,magneticMassArrayMin))
								/10.0
								*Dice.d10());
//Temperature
        gasGiant.setBaseTemperature( (int) (255/sqrt((orbitDistance.doubleValue()/sqrt(orbitingAround.getLumosity().doubleValue())))));

        //TODO here there should be moons generated!!!!

        // Inner group of moonlets,rings and catched stuff
        // Group of major moons
        // outer group of catched asteroids and stuff

        return gasGiant;
	}
}
