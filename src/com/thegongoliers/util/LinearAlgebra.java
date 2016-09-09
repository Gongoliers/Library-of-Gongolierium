package com.thegongoliers.util;
import java.util.Arrays;
import java.util.regex.Matcher;

public class LinearAlgebra {

	public static class Vector {

		private double[] mCoords;

		public Vector(double... coordinates) {
			mCoords = coordinates;
		}

		public int dimension() {
			return mCoords.length;
		}

		public double get(int position) {
			return mCoords[position];
		}

		public void set(int position, double value) {
			mCoords[position] = value;
		}

		public Vector plus(Vector v) {
			if (v.dimension() != dimension())
				return this;
			Vector vect = new Vector();
			vect.mCoords = new double[dimension()];
			for (int i = 0; i < dimension(); i++) {
				vect.set(i, get(i) + v.get(i));
			}
			return vect;
		}

		public Vector minus(Vector v) {
			return plus(v.timesScalar(-1));
		}

		public Vector timesScalar(double value) {
			Vector v = new Vector();
			v.mCoords = new double[dimension()];
			for (int i = 0; i < dimension(); i++) {
				v.set(i, value * get(i));
			}
			return v;
		}

		public double magnitude() {
			double sum = 0;
			for (double coord : mCoords)
				sum += MathExt.square(coord);
			return Math.sqrt(sum);
		}

		public Vector normalized() {
			return timesScalar(1 / magnitude());
		}

		public double dotProduct(Vector v) {
			if (v.dimension() != dimension())
				return 0;

			double sum = 0;
			for (int i = 0; i < dimension(); i++) {
				sum += get(i) * v.get(i);
			}
			return sum;
		}

		public boolean parallel(Vector v) {
			return (isZero() || v.isZero() || MathExt.approxEqual(angle(v), 0, 0.00001)
					|| MathExt.approxEqual(angle(v), Math.PI, 0.00001));
		}

		public boolean orthogonal(Vector v) {
			return MathExt.approxEqual(dotProduct(v), 0, 0.00001);
		}

		public boolean isZero() {
			return MathExt.approxEqual(magnitude(), 0, 0.00001);
		}

		public Vector componentOrthogonal(Vector basis) {
			Vector projection = componentParallel(basis);
			return minus(projection);
		}

		public Vector componentParallel(Vector basis) {
			Vector u = basis.normalized();
			double weight = dotProduct(u);
			return u.timesScalar(weight);

		}

		/**
		 * Computes the angle between this vector and another vector in radians
		 * 
		 * @param v
		 * @return
		 */
		public double angle(Vector v) {
			double dotProduct = dotProduct(v);
			return Math.acos(MathExt.toRange(dotProduct / (magnitude() * v.magnitude()), -1, 1));
		}

		@Override
		public String toString() {
			return "Vector: " + Arrays.toString(mCoords);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Vector))
				return false;
			Vector vect = (Vector) obj;
			if (vect.dimension() != dimension())
				return false;
			for (int i = 0; i < dimension(); i++) {
				if (vect.get(i) != get(i))
					return false;
			}
			return true;
		}
	}

}
