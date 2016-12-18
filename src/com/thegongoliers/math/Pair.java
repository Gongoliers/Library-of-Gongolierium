package com.thegongoliers.math;

public class Pair {

	private double mfirst, msecond;

	public Pair(double first, double second) {
		mfirst = first;
		msecond = second;
	}

	public double getFirst() {
		return mfirst;
	}

	public double getSecond() {
		return msecond;
	}

	public void setFirst(double newValue) {
		mfirst = newValue;
	}

	public void setSecond(double newValue) {
		msecond = newValue;
	}

	@Override
	public String toString() {
		return "(" + getFirst() + ", " + getSecond() + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pair))
			return false;
		Pair pair = (Pair) obj;
		return pair.getFirst() == getFirst() && pair.getSecond() == getSecond();
	}
}