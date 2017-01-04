package com.thegongoliers.input.camera;

import com.thegongoliers.math.MathExt;

public class Scorer {
	/**
	 * Calculates the score of a ratio (observed/ideal) where the further from 1
	 * the ratio is, the lower the ratio. A ratio of 1 will yield a score of
	 * 100.
	 * 
	 * @param observed
	 *            The observed value
	 * @param ideal
	 *            The ideal value
	 * @return The score of how close the observed was to the ideal in the range
	 *         0 to 100 inclusive.
	 */
	public static double score(double observed, double ideal) {
		return MathExt.toRange(100 * (1 - Math.abs(1 - observed / ideal)), 0, 100);
	}
}
