package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Lockable;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class OperateWithLockCommand extends SequentialCommandGroup {

    private final Lockable mLockable;
    private final boolean mLockWhenInterrupted;

    public OperateWithLockCommand(Subsystem subsystem, Lockable lockable, Command operate, double lockDelaySeconds, boolean lockWhenInterrupted) {
        addRequirements(subsystem);
        mLockable = lockable;
        mLockWhenInterrupted = lockWhenInterrupted;
        addCommands(
                new LockCommand(subsystem, lockable),
                new WaitCommand(lockDelaySeconds),
                operate,
                new UnlockCommand(subsystem, lockable),
                new WaitCommand(lockDelaySeconds)
        );
    }

    public <T extends Subsystem & Lockable> OperateWithLockCommand(T subsystem, Command operate, double lockDelaySeconds, boolean lockWhenInterrupted) {
        this(subsystem, subsystem, operate, lockDelaySeconds, lockWhenInterrupted);
    }

    public OperateWithLockCommand(Subsystem subsystem, Lockable lockable, Command operate, double lockDelaySeconds) {
        this(subsystem, lockable, operate, lockDelaySeconds, true);
    }

    public <T extends Subsystem & Lockable> OperateWithLockCommand(T subsystem, Command operate, double lockDelaySeconds) {
        this(subsystem, operate, lockDelaySeconds, true);
    }

    // TODO: Figure this out
    // @Override
    // public void end(boolean interrupted) {
    //     if (interrupted && mLockWhenInterrupted) {
    //         mLockable.lock();
    //     }
    // }
}
