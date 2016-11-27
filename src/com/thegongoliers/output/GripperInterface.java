package com.thegongoliers.output;

public interface GripperInterface extends Stoppable {
	void close();

	void open();

	boolean isClosed();

	boolean isOpened();

}
