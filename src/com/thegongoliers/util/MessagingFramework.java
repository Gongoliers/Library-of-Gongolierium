package com.thegongoliers.util;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MessagingFramework {
	private static HashMap<String, List<Consumer<Object>>> nodes = new HashMap<>();

	public static <T extends Object> void subscribe(String topic, Consumer<T> listener) {
		if (nodes.containsKey(topic)) {
			nodes.get(topic).add((Consumer<Object>) listener);
		} else {
			List<Consumer<Object>> list = new LinkedList<>();
			list.add((Consumer<Object>) listener);
			nodes.put(topic, list);
		}
	}

	public static <T extends Object> void publish(String topic, T data) {
		List<Consumer<Object>> subscribedNodes = nodes.get(topic);
		for (Consumer<Object> node : subscribedNodes)
			node.accept(data);
	}

}
