package com.thegongoliers.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.SpeedController;

public class JoinedSpeedController implements SpeedController {

	private List<SpeedController> mSpeedControllers;
	private boolean mIsInverted = false;

	/**
	 * Joins speed controllers together to easily control all of their outputs
	 * synchronously.
	 */
	public JoinedSpeedController() {
		mSpeedControllers = new ArrayList<>();
	}

	/**
	 * Joins speed controllers together to easily control all of their outputs
	 * synchronously.
	 * 
	 * @param speedControllers
	 *            The speed controllers to control.
	 */
	public JoinedSpeedController(SpeedController... speedControllers) {
		mSpeedControllers = new ArrayList<>();
		Collections.addAll(mSpeedControllers, speedControllers);
	}

	/**
	 * Joins speed controllers together to easily control all of their outputs
	 * synchronously.
	 * 
	 * @param speedControllers
	 *            The speed controllers to control.
	 */
	public JoinedSpeedController(List<SpeedController> speedControllers) {
		mSpeedControllers = speedControllers;
	}

	@Override
	public void pidWrite(double output) {
		mSpeedControllers.forEach(controller -> controller.pidWrite(output));
	}

	public int getNumSpeedControllers() {
		return mSpeedControllers.size();
	}

	@Override
	public double get() {
		if (getNumSpeedControllers() == 0) {
			return 0;
		}
		return mSpeedControllers.get(0).get();
	}

	public void set(double speed, byte syncGroup) {
		set(speed);
	}

	@Override
	public void set(double speed) {
		mSpeedControllers.forEach(controller -> controller.set(speed));
	}

	@Override
	public void setInverted(boolean isInverted) {
		mIsInverted = isInverted;
		mSpeedControllers.forEach(controller -> controller.setInverted(!controller.getInverted()));
	}

	public void setInverted(boolean isInverted, int speedControllerIndex) {
		mSpeedControllers.get(speedControllerIndex).setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return mIsInverted;
	}

	@Override
	public void disable() {
		mSpeedControllers.forEach(SpeedController::disable);
	}

	public void stopMotor() {
		mSpeedControllers.forEach(SpeedController::stopMotor);
	}

}
