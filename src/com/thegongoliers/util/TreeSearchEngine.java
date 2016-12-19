package com.thegongoliers.util;

import java.util.ArrayList;
import java.util.List;

public class TreeSearchEngine {
	public <T> TreeNode<T> findLowestCommonAncestor(TreeNode<T> n1, TreeNode<T> n2) {
		if (n1 == null || n2 == null) {
			return null;
		}
		List<TreeNode<T>> ancestorsN1 = getAllAncestors(n1);
		TreeNode<T> current = n2;
		while (current != null) {
			if (ancestorsN1.contains(current)) {
				return current;
			}
			current = current.getParent();
		}
		return null;
	}

	public <T> List<TreeNode<T>> getAllAncestors(TreeNode<T> node) {
		List<TreeNode<T>> ancestors = new ArrayList<>();
		TreeNode<T> current = node;
		while (current != null) {
			ancestors.add(current);
			current = current.getParent();
		}
		return ancestors;
	}
}
