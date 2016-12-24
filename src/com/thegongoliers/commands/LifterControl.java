package com.thegongoliers.commands;

import com.thegongoliers.input.JoystickInput;
import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LifterControl extends Command {

	private LifterInterface elevator;
	private JoystickInput speed;
	private boolean squared = false;

	public <T extends Subsystem & LifterInterface> LifterControl(T elevator, JoystickInput speed, boolean squared) {
		requires(elevator);
		this.elevator = elevator;
		this.speed = speed;
		this.squared = squared;
	}

	public <T extends Subsystem & LifterInterface> LifterControl(T drive, JoystickInput speed) {
		this(drive, speed, false);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		elevator.up(speed.power(squared ? 2 : 1).read());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		elevator.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
