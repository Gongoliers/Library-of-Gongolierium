package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class AlignTargetCommand extends CommandBase {

    private ModularDrivetrain drivetrain;
    private TargetAlignmentModule module;
    private double desiredTargetArea, desiredHorizontalOffset;

    public AlignTargetCommand(Subsystem subsystem, ModularDrivetrain drivetrain, double desiredTargetArea, double desiredHorizontalOffset){
        addRequirements(subsystem);
        this.drivetrain = drivetrain;
        this.desiredTargetArea = desiredTargetArea;
        this.desiredHorizontalOffset = desiredHorizontalOffset;

        module = drivetrain.getInstalledModule(TargetAlignmentModule.class);
        if (module == null){
            throw new GongolieriumException("The drivetrain does not have a target alignment module installed.");
        }
    }

    @Override
    public void initialize() {
        module.align(desiredHorizontalOffset, desiredTargetArea);
    }

    @Override
    public void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    public void end(boolean interrupted) {
        module.stopAligning();
    }

    @Override
    public boolean isFinished() {
        return !module.isAligning();
    }


}