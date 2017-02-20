package com.thegongoliers.commands;


import com.thegongoliers.output.interfaces.Stoppable;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class StopCommand extends InstantCommand {
	
	private Stoppable system;

	/**
	 * Stop the subsystem. 
	 * @param system The subsystem on the robot which implements the Stoppable interface.
	 */
    public <T extends Subsystem & Stoppable> StopCommand(T system) {
    	requires(system);
    	this.system = system;
    }

    protected void execute() {
    	system.stop();
    }
}
