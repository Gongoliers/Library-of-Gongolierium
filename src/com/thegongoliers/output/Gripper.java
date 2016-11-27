package com.thegongoliers.output;

public interface Gripper extends Stoppable {
	void close();

	void open();

	boolean isClosed();

	boolean isOpened();

}
