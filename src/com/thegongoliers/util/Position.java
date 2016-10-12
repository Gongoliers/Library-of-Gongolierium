package com.thegongoliers.util;


public class Position extends Pair {

	public Position(double x, double y) {
		super(x, y);
	}

	public double getX() {
		return getFirst();
	}

	public double getY() {
		return getSecond();
	}

	public void setX(double x) {
		setFirst(x);
	}

	public void setY(double y) {
		setSecond(y);
	}
	
	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
}
