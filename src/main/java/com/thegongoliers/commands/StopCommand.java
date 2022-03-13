package com.thegongoliers.commands;

import com.thegongoliers.output.interfaces.Stoppable;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;


public class StopCommand extends InstantCommand {
	
	/**
	 * Stop the subsystem. 
	 * @param system The subsystem on the robot which implements the Stoppable interface.
	 */
    public <T extends Subsystem & Stoppable> StopCommand(T system) {
		super(system::stop, system);
    }
}
