package com.thegongoliers.output.interfaces;

public interface GripperInterface extends Stoppable {
	/**
	 * Close the gripper.
	 */
	void close();

	/**
	 * Open the gripper.
	 */
	void open();

	/**
	 * Determine if the gripper is closed
	 * 
	 * @return True of the gripper is closed, false otherwise
	 */
	boolean isClosed();

	/**
	 * Determine if the gripper is open
	 * 
	 * @return True of the gripper is open, false otherwise
	 */
	boolean isOpened();

}
