package com.thegongoliers.input.camera;

public class Scorer {
	public static double score(double observedRatio, double idealRatio) {
		return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - observedRatio/idealRatio)), 100)));
	}
}
