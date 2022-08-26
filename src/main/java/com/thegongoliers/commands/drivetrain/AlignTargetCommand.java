package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.ITargetAligner;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;

import com.thegongoliers.output.drivetrain.swerve.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class AlignTargetCommand extends CommandBase {

    private ModularDrivetrain drivetrain;
    private SwerveDrivetrain swerve;
    private final ITargetAligner aligner;
    private final double desiredTargetArea;
    private final double desiredHorizontalOffset;

    public AlignTargetCommand(Subsystem subsystem, ModularDrivetrain drivetrain, double desiredTargetArea, double desiredHorizontalOffset) {
        addRequirements(subsystem);
        this.drivetrain = drivetrain;
        this.desiredTargetArea = desiredTargetArea;
        this.desiredHorizontalOffset = desiredHorizontalOffset;

        aligner = drivetrain.getInstalledModule(ITargetAligner.class);
        if (aligner == null) {
            throw new GongolieriumException("The drivetrain does not have a target alignment module installed.");
        }
    }

    public AlignTargetCommand(Subsystem subsystem, SwerveDrivetrain drivetrain, double desiredTargetArea, double desiredHorizontalOffset) {
        addRequirements(subsystem);
        this.swerve = drivetrain;
        this.desiredTargetArea = desiredTargetArea;
        this.desiredHorizontalOffset = desiredHorizontalOffset;

        aligner = drivetrain.getInstalledModule(ITargetAligner.class);
        if (aligner == null) {
            throw new GongolieriumException("The drivetrain does not have a target alignment module installed.");
        }
    }

    @Override
    public void initialize() {
        aligner.align(desiredHorizontalOffset, desiredTargetArea);
    }

    @Override
    public void execute() {
        // Causes the module to run, ignores input values
        if (drivetrain != null) {
            drivetrain.arcade(0, 0);
        } else if (swerve != null) {
            swerve.drive(0, 0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        aligner.stopAligning();
    }

    @Override
    public boolean isFinished() {
        return !aligner.isAligning();
    }


}