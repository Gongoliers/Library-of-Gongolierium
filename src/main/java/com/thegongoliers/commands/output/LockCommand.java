package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Lockable;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class LockCommand extends InstantCommand {

    public LockCommand(Subsystem subsystem, Lockable lockable) {
        super(lockable::lock, subsystem);
    }

    public <T extends Subsystem & Lockable> LockCommand(T subsystem) {
        this(subsystem, subsystem);
    }
}
