package com.github.hteph.ObjectsOfAllSorts;

import java.io.Serializable;

public class AtmosphericGases implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int percentageInAtmo;



//	public AtmosphericGases(String name, int percentageInAtmo) {
//
//		this.name = name;
//		this.percentageInAtmo = percentageInAtmo;
//	}

    public AtmosphericGases(Builder builder) {

        this.name = builder.name;
        this.percentageInAtmo = builder.percentageInAtmo;
    }

//Getters and Setters-----------------------------------------------------------------------------------------


	public String getName() {
		return name;
	}

	public int getPercentageInAtmo() {
		return percentageInAtmo;
	}

	public void setPercentageInAtmo(int percentageInAtmo) {
		this.percentageInAtmo = percentageInAtmo;
	}

    public static Builder builder() {
        return new Builder();
    }

	@Override
	public String toString() {
		String atmoString= name + " (" + percentageInAtmo + " %)";
		return atmoString;
	}

    public static class Builder {
        private String name;
        private int percentageInAtmo;

        private Builder(){

        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPercentageInAtmo(int percentageInAtmo) {
            this.percentageInAtmo = percentageInAtmo;
            return this;
        }

        public AtmosphericGases build() {
            return new AtmosphericGases(this);
        }
    }
}
