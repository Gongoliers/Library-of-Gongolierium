package com.thegongoliers.commands;

import com.thegongoliers.output.interfaces.Disableable;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A command which disables a subsystem
 */
public class DisableCommand extends InstantCommand {

    private Disableable disableable;

    /**
     * Default constructor
     * @param subsystem the subsystem to disable
     */
    public  <T extends Subsystem & Disableable> DisableCommand(T subsystem){
        requires(subsystem);
        disableable = subsystem;
    }

    @Override
    protected void initialize() {
        disableable.disable();
    }
}
