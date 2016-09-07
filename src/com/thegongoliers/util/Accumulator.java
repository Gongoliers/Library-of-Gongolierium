package com.thegongoliers.util;

public class Accumulator {
	private double sum = 0;

	public Accumulator() {
		sum = 0;
	}

	public Accumulator(double initialSum) {
		sum = initialSum;
	}

	/**
	 * add : value -> void
	 * 
	 * Accumulates the given value
	 * 
	 * @param value
	 */
	public void add(double value) {
		sum += value;
	}

	/**
	 * get : this -> double
	 * 
	 * Produces the accumulated sum
	 * 
	 * @return The sum
	 */
	public double get() {
		return sum;
	}

	/**
	 * reset : this -> void
	 * 
	 * Resets the sum to 0
	 */
	public void reset() {
		sum = 0;
	}

}
