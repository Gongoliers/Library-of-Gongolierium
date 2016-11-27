package com.thegongoliers.output;

public interface FlywheelInterface extends Stoppable {

	void spinUp(double speed);

	void spinToSpeed(double speed);

	double getSpeed();

}
