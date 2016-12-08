package com.thegongoliers.util;

import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private static class Node<T, U> {
		Node<T, U> next;
		Node<T, U> previous;
		U data;
		T key;
	}

	private static class DLinkedList<T, U> {
		Node<T, U> head;
		Node<T, U> tail;

		// remove
		public Node<T, U> remove(Node<T, U> aNode) {
			if (aNode.previous != null)
				aNode.previous.next = aNode.next;
			else {
				head = aNode.next;
				head.previous = null;
			}
			if (aNode.next != null)
				aNode.next.previous = aNode.previous;
			else {
				tail = aNode.previous;
				tail.next = null;
			}
			aNode.previous = null;
			aNode.next = null;
			return aNode;
		}

		// add
		public void add(Node<T, U> aNode) {
			if (head == null) {
				head = aNode;
				tail = aNode;
			} else {
				aNode.previous = tail;
				tail.next = aNode;
				tail = aNode;
			}
		}

		// getfirst
		public Node<T, U> getFirst() {
			return head;
		}
	}

	private HashMap<T, Node<T, U>> evictorMap;
	private DataProvider<T, U> provider;
	private int capacity;
	private int misses;
	private DLinkedList<T, U> cache;

	/**
	 * @param provider
	 *            the data provider to consult for a cache miss
	 * @param capacity
	 *            the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache(DataProvider<T, U> provider, int capacity) {
		evictorMap = new HashMap<>();
		this.provider = provider;
		this.capacity = capacity;
		cache = new DLinkedList<>();
	}

	/**
	 * Returns the value associated with the specified key.
	 * 
	 * @param key
	 *            the key
	 * @return the value associated with the key
	 */
	public U get(T key) {
		if (evictorMap.containsKey(key)) { // hit
			// Get from hm
			Node<T, U> node = evictorMap.get(key);
			// Move linked list value to end
			Node<T, U> removedNode = cache.remove(node);
			cache.add(removedNode);
			return node.data;
		} else { // miss
			Node<T, U> node = new Node<>();
			node.data = provider.get(key);
			node.key = key;
			int currentCapacity = evictorMap.size();
			if (currentCapacity == capacity) {
				Node<T, U> oldNode = cache.getFirst();
				cache.remove(oldNode);
				evictorMap.remove(oldNode.key);
			}
			// add node to linked list
			cache.add(node);
			evictorMap.put(key, node);
			misses++;
			return node.data;
		}
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * 
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses() {
		return misses;
	}
}
