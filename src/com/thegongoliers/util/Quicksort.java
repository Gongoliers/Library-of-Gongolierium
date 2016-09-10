package com.thegongoliers.util;

import java.util.ArrayList;
import java.util.List;

public class Quicksort {

	public static double[] sort(double[] values) {
		if (values.length == 0) {
			return values;
		}
		return append(append(sort(smaller(values, values[0])), new double[] { values[0] }),
				sort(larger(values, values[0])));
	}

	private static double[] smaller(double[] v, double thresh) {
		List<Double> values = new ArrayList<>();
		for (double val : v) {
			if (val < thresh)
				values.add(val);
		}
		double[] vals = new double[values.size()];
		for (int i = 0; i < values.size(); i++) {
			vals[i] = values.get(i);
		}
		return vals;
	}

	private static double[] larger(double[] v, double thresh) {
		List<Double> values = new ArrayList<>();
		for (double val : v) {
			if (val > thresh)
				values.add(val);
		}
		double[] vals = new double[values.size()];
		for (int i = 0; i < values.size(); i++) {
			vals[i] = values.get(i);
		}
		return vals;
	}

	private static double[] append(double[] v1, double[] v2) {
		double[] values = new double[v1.length + v2.length];
		for (int i = 0; i < v1.length; i++) {
			values[i] = v1[i];
		}
		for (int j = 0; j < v2.length; j++) {
			values[v1.length + j] = v2[j];
		}

		return values;
	}

}
