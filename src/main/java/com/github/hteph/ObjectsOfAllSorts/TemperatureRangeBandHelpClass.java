package com.github.hteph.ObjectsOfAllSorts;


import java.math.BigDecimal;

public class TemperatureRangeBandHelpClass {
	
	public String name;
	private Integer one;
	private double two;
	private double three;
	private double four;
	private double five;
	private double six;
	private double seven;
	private double eight;
	private double nine;
	private double ten;
	
	public TemperatureRangeBandHelpClass(String name, int[] temp) {
		
		this.name = name;
		one = temp[0];
		two= temp[1];
		three= temp[2];
		four= temp[3];
		five= temp[4];
		six= temp[5];
		seven= temp[6];
		eight= temp[7];
		nine= temp[8];
		ten= temp[9];
		
	}

	@Override
	public String toString() {
		return "TemperatureRangeBandHelpClass [one=" + one + ", two=" + two + ", three=" + three + ", four=" + four
				+ ", five=" + five + ", six=" + six + ", seven=" + seven + ", eight=" + eight + ", nine=" + nine
				+ ", ten=" + ten + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOne() {
		return one;
	}

	public void setOne(Integer one) {
		this.one = one;
	}

	public double getTwo() {
		return two;
	}

	public void setTwo(double two) {
		this.two = two;
	}

	public double getThree() {
		return three;
	}

	public void setThree(double three) {
		this.three = three;
	}

	public double getFour() {
		return four;
	}

	public void setFour(double four) {
		this.four = four;
	}

	public double getFive() {
		return five;
	}

	public void setFive(double five) {
		this.five = five;
	}

	public double getSix() {
		return six;
	}

	public void setSix(double six) {
		this.six = six;
	}

	public double getSeven() {
		return seven;
	}

	public void setSeven(double seven) {
		this.seven = seven;
	}

	public double getEight() {
		return eight;
	}

	public void setEight(double eight) {
		this.eight = eight;
	}

	public double getNine() {
		return nine;
	}

	public void setNine(double nine) {
		this.nine = nine;
	}

	public double getTen() {
		return ten;
	}

	public void setTen(double ten) {
		this.ten = ten;
	}

	
	
}
