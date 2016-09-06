package com.thegongoliers.util;

import java.util.ArrayList;
import java.util.List;

public class AdvancedMath {

	private AdvancedMath() {
	}

	/**
	 * isOdd : int -> boolean
	 * 
	 * Determines if a number is odd
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isOdd(int value) {
		return value % 2 == 1;
	}

	/**
	 * isEven : int -> boolean
	 * 
	 * Determines if a number is even
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEven(int value) {
		return !isOdd(value);
	}

	/**
	 * isPartOfNumber : char -> boolean
	 * 
	 * Determines if a symbol is a number, or part of a number such as - and .
	 * 
	 * @param symbol
	 * @return
	 */
	static boolean isPartOfNumber(char symbol) {
		return Character.isDigit(symbol) || symbol == '-' || symbol == '.';
	}

	public static PolarCoordinates toPolar(Position cartesian) {
		double magnitude = Math.sqrt(Math.pow(cartesian.getX(), 2) + Math.pow(cartesian.getY(), 2));
		double angle = Math.atan2(cartesian.getY(), cartesian.getX());

		return new PolarCoordinates(magnitude, angle);

	}

}
