package com.thegongoliers.math;

import java.util.Arrays;

public class LinearAlgebra {
	
	public static final Vector i = new Vector(1, 0, 0);
	public static final Vector j = new Vector(0, 1, 0);
	public static final Vector k = new Vector(0, 0, 1);

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
				throw new RuntimeException("Vectors were of different lengths");
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
		
		public Vector crossProduct(Vector v) {
			if(dimension() != v.dimension() & dimension() != 3){
				throw new RuntimeException("Vectors were not both 3 dimensional");
			}
			Vector cross = new Vector(0, 0, 0);
			cross.set(0, get(1) * v.get(2) - get(1) * v.get(2));
			cross.set(1, get(0) * v.get(2) - get(2) * v.get(0));
			cross.set(2, get(0) * v.get(1) - get(1) * v.get(0));
			return cross;
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
