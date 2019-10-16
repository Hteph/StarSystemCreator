package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.*;

import com.github.hteph.Tables.Creature.BaseEnvironmentTable;
import com.github.hteph.Tables.Creature.EnvironmentalAttributesTable;
import com.github.hteph.Tables.TableMaker;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.enums.*;

public class CreatureGenerator {

    private CreatureGenerator() {
        //No instances please
    }

    public static Sophont generator(StellarObject place) {

        Sophont lifeform= new Sophont(place);
        BaseEnvironmentTable environment = new BaseEnvironmentTable(place);
        EnvironmentalEnum[] baseEnvironment = environment.findBaseEnvironment();

        if(!(baseEnvironment[1]==EnvironmentalEnum.NONE)) {
            lifeform.addAttribute("Dual Environment",baseEnvironment[0].getDescription()+"("+baseEnvironment[1]+")");
            lifeform.setHabitat(baseEnvironment[1]);
        }else lifeform.setHabitat(baseEnvironment[0]);

        EnvironmentalAttributesTable.environmentalAttributes(lifeform, baseEnvironment);
        decideMetabolism(lifeform);
        gravityEffects(lifeform);

        basicBodyShape(lifeform);

        return lifeform;

    }

    private static Sophont basicBodyShape(Sophont lifeform) {
        int bonus=0;
        int bonus2=0;
        lifeform.setBody(new CreatureBody());

        if(lifeform.hasAttribute("climber")
                || lifeform.hasAttribute("brachiator")) bonus+=3;
        if (lifeform.hasAttribute("flier")
                && lifeform.getAttributes().get("flier").hasCondition("winged")) bonus+=3;

        if (lifeform.hasAttribute("Aquatic")) bonus -=3;
        if(lifeform.getHabitat().equals(EnvironmentalEnum.EXOTIC)) bonus2 -=6;


        if(Dice._3d6lessThan(16+bonus+bonus2)) {
            lifeform.getBody().setBodySymmetry(Symmetry.BILATERAL);
            lifeform.getBody().setLimbSegments(TableMaker.makeRoll(
                    Dice.d6(),
                    new int[]{1,2,6},
                    new Integer[]{1,2,Dice.d6()+2}));

        } else if(Dice._3d6lessThan(16+bonus2)){
            int sides = Dice.d6()+2;
            switch(sides){
                case 3: lifeform.getBody().setBodySymmetry(Symmetry.TRILATERAL);
                    break;
                case 4: lifeform.getBody().setBodySymmetry(Symmetry.QUADRAL);
                    break;
                case 5: lifeform.getBody().setBodySymmetry(Symmetry.PENTRADAL);
                    break;
                default:
                    lifeform.getBody().setBodySymmetry(Symmetry.RADIAL);
                    break;
            }
            lifeform.getBody().setLimbSegments(TableMaker.makeRoll(
                    Dice._2d6(),
                    new int[]{2,6,10},
                    new Integer[]{1,2,Dice.d6()+2}));
        } else {
            lifeform.getBody().setBodySymmetry(Symmetry.NONE);
            lifeform.getBody().setLimbSegments(0);
        }

        return lifeform;
    }

    private static void gravityEffects(Sophont lifeform){

    double gravity =((Planet)(CentralRegistry.getFromArchive(lifeform.getHomeworld()))).getGravity().doubleValue();
    int roll=Dice._3d6();

    if(gravity<0.7) {
        roll +=3;
        lifeform.addAttribute(Attributes.STRENGTH, -1);
        lifeform.addAttribute(Attributes.CONSTITUTION, -1);
        lifeform.addAttribute("G-Tolerance", -1,"XXX");
        lifeform.addAttribute(Attributes.FRAME,3);

    }else if (gravity>1.5) {
        roll +=-3;
        lifeform.addAttribute("G-Tolerance", 1,"XXX");
        lifeform.addAttribute(Attributes.STRENGTH);
        lifeform.addAttribute(Attributes.CONSTITUTION);
        lifeform.addAttribute(Attributes.FRAME,-3);
    }

    if(roll<6) lifeform.addAttribute("Acceleration Weakness", "XXX");
    else if(roll>16) lifeform.addAttribute("Space Sickness","XXX");
    else if(roll>14) lifeform.addAttribute("Acceleration Tolerance","XXX");
}

    private static void decideMetabolism(Sophont lifeform) {

        int bonus=0;
        if(lifeform.isClimatePref(ClimatePref.COLD)) bonus +=1;
        if(lifeform.hasAttribute("Aquatic")) bonus -=1;
        if(lifeform.hasAttribute("Desert Dweller")) bonus -=1;
        if(lifeform.hasAttribute("Chaser")) bonus +=1;
        if(lifeform.hasAttribute("Pouncer")) bonus +=1;
        if(lifeform.hasAttribute("Herder")) bonus +=1;
        if(lifeform.hasAttribute("Ergivore")) bonus -=2;
        if(lifeform.hasAttribute("Flier")) bonus +=1;

        String choice=TableMaker.makeRoll(
                        Dice.d6()+bonus,
                        new int[]{0, 2, 4, 6},
                        new String[]{"Slow Metabolism", "Cold Blooded", "Warm Blooded", "Hyperactive"}
                );
        switch (choice){
            case "Slow Metabolism":
                lifeform.addAttribute("Slow Metabolism","The metabolic processes are so slow that the lifeform seems to live in another time paradigm. ");
                lifeform.addAttribute(Attributes.SPEED,-2).addToDescription("The slow metabolism of this creature lowers its reaction times");
                lifeform.addAttribute(Attributes.LIFESPAN,2);
                lifeform.addAttribute(Attributes.SUSTENANCE,-2);
                break;
            case "Cold Blooded":
                lifeform.addAttribute("Cold Blooded","The lifeform is dependent on the ambient temperatue to be above a certain temperature threshold.");
                lifeform.addAttribute("Ectothermy", "Controlling body temperature through external metabolic processes, such as by basking in the sun");
                lifeform.addAttribute(Attributes.LIFESPAN,1);
                lifeform.addAttribute(Attributes.SUSTENANCE,-1);
                break;
            case "Warm Blooded":
                lifeform.addAttribute("Warm Blooded","The metabolism of this lifeform can support it in a wide range of temperatures");
                break;
            case "Hyperactive":
                lifeform.addAttribute("Hyperactive","Is equally home in water as on land");
                lifeform.addAttribute("Homeothermy","The lifeform maintains a stable internal body temperature regardless of external influence.");
                lifeform.addAttribute(Attributes.SPEED,1);
                lifeform.addAttribute(Attributes.LIFESPAN,-1);
                lifeform.addAttribute(Attributes.SUSTENANCE,2);
                break;
        }
    }
}
