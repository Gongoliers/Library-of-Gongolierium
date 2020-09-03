package com.thegongoliers.commands;

import com.thegongoliers.output.interfaces.Disableable;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A command which disables a subsystem
 */
public class DisableCommand extends InstantCommand {

    /**
     * Default constructor
     * @param subsystem the subsystem to disable
     */
    public  <T extends Subsystem & Disableable> DisableCommand(T subsystem){
        super(() -> subsystem.disable(), subsystem);
    }
}
