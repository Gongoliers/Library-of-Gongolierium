package com.thegongoliers.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An equation accepts a polynomial as a string in the form ax^b+cx^d-ex^f+g It
 * represents a mathematical equation
 * 
 * @author Kylec
 *
 */
public class Equation {

	private List<Double> terms = new ArrayList<>();

	public Equation(String equation) {
		terms = parseEquation(equation);
	}

	/**
	 * eval : double -> double
	 * 
	 * Calculates the equation at the x position specified
	 * 
	 * @param x
	 * @return
	 */
	public double eval(double x) {
		double sum = 0;
		for (int i = 0; i < terms.size(); i += 2) {
			sum += terms.get(i) * Math.pow(x, terms.get(i + 1));
		}
		return sum;
	}

	/**
	 * parseEquation : String -> List<Double>
	 * 
	 * Parses a list of equation terms from a String
	 * 
	 * @param equation
	 * @return
	 */
	private List<Double> parseEquation(String equation) {

		List<Double> parsedTerms = new ArrayList<Double>();

		int startPos = 0;
		equation = equation.trim().replace(" ", "");
		equation = equation.replace("-", "+-");
		for (int i = 0; i < equation.length(); i++) {
			char current = equation.charAt(i);
			if (MathExt.isPartOfNumber(current)) {

			} else if (i >= 1 && MathExt.isPartOfNumber(equation.charAt(i - 1))) {
				parsedTerms.add(Double.valueOf(equation.substring(startPos, i)));
				startPos = i + 1;
			} else {
				startPos = i + 1;
			}

			if (i == equation.length() - 1) {
				if (equation.charAt(i) == 'x') {
					parsedTerms.add(1.0);
				} else {
					parsedTerms.add(Double.valueOf(equation.substring(startPos, i + 1)));
				}
			}
		}

		if (MathExt.isOdd(parsedTerms.size())) {
			parsedTerms.add(0.0);
		}

		return parsedTerms;
	}

	@Override
	public String toString() {
		String equationString = "";
		for (int i = 0; i < terms.size(); i += 2) {
			equationString += Double.toString(terms.get(i));
			if (terms.get(i + 1) != 1) {
				if (terms.get(i + 1) != 0) {
					equationString += "x^" + Double.toString(terms.get(i + 1));
				}
			} else {
				equationString += "x";
			}
			if (i != terms.size() - 2) {
				equationString += " + ";
			}
		}
		equationString = equationString.replace(" + -", " - ");
		return equationString.trim();
	}

	/**
	 * add : Equation -> Equation
	 * 
	 * Computes the sum of two equations
	 * 
	 * @param eq
	 * @return
	 */
	public Equation add(Equation eq) {
		Equation sum = new Equation("");
		for (int i = 0; i < eq.terms.size(); i++) {
			sum.terms.add(eq.terms.get(i));
		}
		for (int i = 0; i < terms.size(); i += 2) {
			boolean added = false;
			for (int j = sum.terms.size() - 2; j >= 0; j -= 2) {
				if (sum.terms.get(j + 1).equals(terms.get(i + 1))) {
					sum.terms.set(j, sum.terms.get(j) + terms.get(i));
					added = true;
					break;
				} else if (sum.terms.get(j + 1) > terms.get(i + 1)) {
					sum.terms.add(j, terms.get(i));
					sum.terms.add(j + 1, terms.get(i + 1));
					added = true;
					break;
				}
			}
			if (!added) {
				sum.terms.add(terms.get(i));
				sum.terms.add(terms.get(i + 1));
			}
		}

		return sum;
	}

	/**
	 * derivative : this -> Equation
	 * 
	 * Calculates the derivative of the equation
	 * 
	 * @param eq
	 * @return
	 */
	public Equation derivative() {
		Equation deriv = new Equation("");

		for (int i = 0; i < terms.size(); i += 2) {
			double derivCoef = terms.get(i) * terms.get(i + 1);
			if (derivCoef != 0) {
				deriv.terms.add(i, derivCoef);
				deriv.terms.add(i + 1, terms.get(i + 1) - 1);
			}
		}

		return deriv;
	}

	/**
	 * integral : this -> Equation
	 * 
	 * Calculates the integral of the equation
	 * 
	 * @param eq
	 * @return
	 */
	public Equation integral() {
		Equation integ = new Equation("");

		for (int i = 0; i < terms.size(); i += 2) {
			double integCoef = terms.get(i) / (terms.get(i + 1) + 1);
			if (integCoef != 0) {
				integ.terms.add(i, integCoef);
				integ.terms.add(i + 1, terms.get(i + 1) + 1);
			}
		}

		return integ;
	}

}