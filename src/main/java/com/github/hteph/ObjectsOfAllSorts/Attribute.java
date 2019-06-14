package com.github.hteph.ObjectsOfAllSorts;

import com.github.hteph.Utilities.enums.Attributes;
import com.github.hteph.Utilities.enums.baseEnum;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
	
	private String name;
	private String description;
	private int level;
	private HashMap<String, Attribute> conditions;
	private Attributes enumCode;

    public Attribute(String name, String description) {
		
		this.name=name;
		this.description = description;
		this.level=1;
		conditions = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public Attribute increaseLevel() {
		this.level++;
        return this;
    }
	
	public Attribute decreaseLevel() {
		this.level--;
        return this;
    }

	public HashMap<String, Attribute> getConditions() {
		return new HashMap<String,Attribute>(conditions);
	}

	public Attribute addCondition(String name, String description) {
		int i=0;
		String suffix="";
		while(hasCondition(name)) {
        i++;
        suffix="+"+i;
		}

        conditions.putIfAbsent(name+suffix, new Attribute(name+suffix, description));
        return this;
	}

	public Attribute addToDescription(String story){

	    if (!description.contains(story)) description +="\n"+story;

	    return this;
    }

	public boolean hasCondition(String name) { return conditions.containsKey(name); }

    @Override
    public String toString() {
        StringBuilder attributeDesc=new StringBuilder();

        for (Map.Entry<String, Attribute> entry : getConditions().entrySet()) {

            attributeDesc.append(entry.getValue().toString()).append("\n");
        }

        final StringBuilder sb = new StringBuilder("Attributes")
        .append("name= ").append(name).append("\n")
        .append("description= ").append(description).append("\n")
        .append("level= ").append(level).append("\n")
        .append("conditions= ").append(attributeDesc.toString()).append("\n")
        .append("-------------");

        return sb.toString();
    }

    public Attributes getEnumCode() {
        return enumCode;
    }

    public Attribute setEnumCode(baseEnum enumCode) {

        if(enumCode instanceof Attributes) this.enumCode = (Attributes)enumCode;

        return this;
    }
}
