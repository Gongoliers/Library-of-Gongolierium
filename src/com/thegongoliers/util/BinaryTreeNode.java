package com.thegongoliers.util;
public class BinaryTreeNode<T> {

	private T data;
	private BinaryTreeNode<T> parent, leftChild, rightChild;

	public BinaryTreeNode(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public BinaryTreeNode<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BinaryTreeNode<T> leftChild) {
		this.leftChild = leftChild;
	}

	public BinaryTreeNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BinaryTreeNode<T> rightChild) {
		this.rightChild = rightChild;
	}

	public BinaryTreeNode<T> getParent() {
		return parent;
	}

	public void setParent(BinaryTreeNode<T> parent) {
		this.parent = parent;
	}

}
