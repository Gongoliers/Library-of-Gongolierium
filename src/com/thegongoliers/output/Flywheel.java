package com.thegongoliers.output;

public interface Flywheel {

	public void spinUp(double speed);

	public void stop();

	public void spinToSpeed(double speed);
	
	public double getSpeed();

}
