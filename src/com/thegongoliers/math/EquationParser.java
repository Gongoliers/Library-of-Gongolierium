package com.thegongoliers.math;
import java.util.function.Function;

import com.thegongoliers.util.BinaryTreeNode;

public class EquationParser {

	public Function<Double, Double> parseEquation(String eq) {
		BinaryTreeNode<String> asTree = convertToTree(eq);

		return new Function<Double, Double>() {

			@Override
			public Double apply(Double t) {
				return eval(asTree, t);
			}
		};
	}

	private double eval(BinaryTreeNode<String> eq, double x) {
		if (eq.getData().equals("+")) {
			return eval(eq.getLeftChild(), x) + eval(eq.getRightChild(), x);
		} else if (eq.getData().equals("^")) {
			return eval(eq.getLeftChild().getLeftChild(), x) * Math.pow(x, eval(eq.getRightChild(), x));
		} else if (eq.getData().equals("x")) {
			if (eq.getLeftChild() != null)
				return eval(eq.getLeftChild(), x) * x;
			return x;
		} else if (isDigit(eq.getData())) {
			return Double.valueOf(eq.getData());
		} else {
			return 0;
		}
	}

	private BinaryTreeNode<String> convertToTree(String eq) {
		int plus = eq.indexOf('+');
		int power = eq.indexOf('^');
		BinaryTreeNode<String> node = new BinaryTreeNode<String>(null);
		if (eq.length() == 0)
			return null;
		if (plus >= 0) {
			String first = eq.substring(0, plus);
			String rest = eq.substring(plus + 1);
			node.setData("+");
			node.setLeftChild(convertToTree(first));
			node.setRightChild(convertToTree(rest));
			node.getRightChild().setParent(node);
			node.getLeftChild().setParent(node);
		} else if (power >= 0) {
			String first = eq.substring(0, power);
			String rest = eq.substring(power + 1);
			node.setData("^");
			node.setLeftChild(convertToTree(first));
			node.setRightChild(convertToTree(rest));
			node.getRightChild().setParent(node);
			node.getLeftChild().setParent(node);
		} else if (eq.charAt(eq.length() - 1) == 'x') {
			String first = eq.substring(0, eq.length() - 1);
			node.setData("x");
			node.setLeftChild(convertToTree(first));
			node.getLeftChild().setParent(node);
		} else if (isDigit(eq)) {
			node.setData(eq);
		}
		return node;
	}

	private boolean isDigit(String eq) {
		try {
			Double.valueOf(eq);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
