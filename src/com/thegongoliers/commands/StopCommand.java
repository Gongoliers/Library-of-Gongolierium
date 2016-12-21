package com.thegongoliers.commands;


import com.thegongoliers.output.Stoppable;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class StopCommand extends Command {
	
	private Stoppable system;

	/**
	 * Stop the subsystem. 
	 * @param system The subsystem on the robot which implements the Stoppable interface.
	 */
    public <T extends Subsystem & Stoppable> StopCommand(T system) {
    	requires(system);
    	this.system = system;
    }

    protected void initialize() {
    	system.stop();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
