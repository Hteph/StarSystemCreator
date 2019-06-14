package com.github.hteph.Utilities;

public final class Dice {
	
	
	private Dice() {
		
		//No instances of this class please
	}
	
	public static int d6(){

		return (int)(1+Math.random()*6);
	}

    public static boolean d6(int lessThan){

        return d6()<lessThan;
    }
	
	public static int _2d6(){
		
	return d6()+d6();	
	
	}
    public static boolean _2d6(int lessThan){

        return (d6()+d6())<lessThan;

    }
	
	public static int _3d6(){
		
	return d6()+d6()+d6();	
	
	}
	
	public static int d10(){

		return (int)(1+Math.random()*10);
	}

	public static boolean _3d6(int lower, int upper){

		int test = d6()+d6()+d6();

		return test > lower & test < upper;

	}

	public static boolean _3d6(int lessThan){

		int test = d6()+d6()+d6();

		return test < lessThan;

	}
}
