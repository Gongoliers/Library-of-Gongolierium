package com.thegongoliers.output.subsystems;

import com.thegongoliers.output.Piston;
import com.thegongoliers.output.interfaces.GripperInterface;

public class PneumaticGripper implements GripperInterface {

	private final Piston piston;

	/**
	 * Creates a pneumatic gripper from a piston.
	 * 
	 * @param piston
	 *            The gripper's piston.
	 */
	public PneumaticGripper(Piston piston) {
		this.piston = piston;
	}

	@Override
	public void stop() {
	}

	@Override
	public void close() {
		piston.extend();
	}

	@Override
	public boolean isClosed() {
		return piston.isExtended();
	}

	@Override
	public boolean isOpened() {
		return piston.isRetracted();
	}

	@Override
	public void open() {
		piston.retract();
	}

}