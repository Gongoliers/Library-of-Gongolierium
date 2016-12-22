package com.thegongoliers.util;

import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class GongolieriumRobot extends IterativeRobot {
	public abstract OI getOI();

	public abstract void initializeSubsystems();
	
	public abstract void setupAutonomousChooser();

	@Override
	public void robotInit() {
		OI oi = getOI();
		oi.setupControllers();
		initializeSubsystems();
		oi.bindCommands();
		setupAutonomousChooser();	
	}
}
