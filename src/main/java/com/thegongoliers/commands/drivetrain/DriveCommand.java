package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.output.interfaces.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {

    private final Drivetrain mDrivetrain;
    private final DoubleSupplier mSpeed;
    private final DoubleSupplier mTurn;

    public DriveCommand(Subsystem subsystem, Drivetrain drivetrain, double speed, double turn) {
        this(subsystem, drivetrain, () -> speed, () -> turn);
    }

    public DriveCommand(Subsystem subsystem, Drivetrain drivetrain, DoubleSupplier speed, DoubleSupplier turn) {
        addRequirements(subsystem);
        mDrivetrain = drivetrain;
        mSpeed = speed;
        mTurn = turn;
    }

    @Override
    public void execute() {
        mDrivetrain.arcade(mSpeed.getAsDouble(), mTurn.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        mDrivetrain.stop();
    }
}
