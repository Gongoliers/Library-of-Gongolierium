package com.thegongoliers.subsystems;

import com.thegongoliers.output.GripperInterface;
import com.thegongoliers.output.Solenoid;

import edu.wpi.first.wpilibj.command.Command;

public class PneumaticGripper extends Gripper implements GripperInterface {

	private final Solenoid solenoid;
	private Command defaultCmd;

	public PneumaticGripper(Solenoid solenoid) {
		this.solenoid = solenoid;
	}

	public PneumaticGripper(Solenoid solenoid, Command defaultCmd) {
		this.solenoid = solenoid;
		this.defaultCmd = defaultCmd;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(defaultCmd);
	}

	@Override
	public void stop() {
	}

	@Override
	public void close() {
		solenoid.extend();
	}

	@Override
	public boolean isClosed() {
		return solenoid.isExtended();
	}

	@Override
	public boolean isOpened() {
		return solenoid.isRetracted();
	}

	@Override
	public void open() {
		solenoid.retract();
	}
	
	@Override
	public void setDefaultCommand(Command command) {
		super.setDefaultCommand(command);
	}

}