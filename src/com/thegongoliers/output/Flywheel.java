package com.thegongoliers.output;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Flywheel extends Subsystem {

	public abstract void spinUp(double speed);

	public abstract void stop();

	public abstract void spinToSpeed(double speed);
	
	public abstract double getSpeed();

}
