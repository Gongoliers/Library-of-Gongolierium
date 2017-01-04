package com.thegongoliers.math;

import java.util.ArrayList;
import java.util.List;

class TreeNode<T> {

	private T data;
	private TreeNode<T> parent;
	private List<TreeNode<T>> children;

	public TreeNode(T data) {
		this.data = data;
		children = new ArrayList<>();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

}
