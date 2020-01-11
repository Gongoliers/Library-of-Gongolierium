package com.thegongoliers.commands;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowerModule;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;
import com.thegongoliers.paths.SimplePath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AlignTargetCommand extends Command {

    private ModularDrivetrain drivetrain;
    private TargetAlignmentModule module;
    private double desiredTargetArea, desiredHorizontalOffset;

    public AlignTargetCommand(Subsystem subsystem, ModularDrivetrain drivetrain, double desiredTargetArea, double desiredHorizontalOffset){
        requires(subsystem);
        this.drivetrain = drivetrain;
        this.desiredTargetArea = desiredTargetArea;
        this.desiredHorizontalOffset = desiredHorizontalOffset;

        module = drivetrain.getInstalledModule(TargetAlignmentModule.class);
        if (module == null){
            throw new GongolieriumException("The drivetrain does not have a target alignment module installed.");
        }
    }

    @Override
    protected void initialize() {
        module.align(desiredHorizontalOffset, desiredTargetArea);
    }

    @Override
    protected void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    protected void end() {
        module.stopAligning();
    }

    @Override
    protected boolean isFinished() {
        return !module.isAligning();
    }


}