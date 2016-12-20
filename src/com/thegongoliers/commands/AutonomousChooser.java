package com.thegongoliers.commands;

import com.thegongoliers.output.Displayable;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousChooser implements Displayable {

	private SendableChooser chooser;
	private String name;

	public AutonomousChooser(Command defaultCmd, Command... others) {
		this("Autonomous Commands", defaultCmd, others);
	}

	public AutonomousChooser(String name, Command defaultCmd, Command... others) {
		this.name = name;
		chooser = new SendableChooser();
		setDefault(defaultCmd);
		for (Command cmd : others) {
			add(cmd);
		}
	}

	public void setDefault(Command cmd) {
		chooser.addDefault(cmd.getName(), cmd);
	}

	public void add(Command cmd) {
		chooser.addObject(cmd.getName(), cmd);
	}

	public void display() {
		SmartDashboard.putData(name, chooser);
	}

	public Command getSelected() {
		return (Command) chooser.getSelected();
	}
}
