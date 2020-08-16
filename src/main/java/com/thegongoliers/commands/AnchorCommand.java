package com.thegongoliers.commands;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.AnchorModule;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class AnchorCommand extends CommandBase {

    private ModularDrivetrain drivetrain;
    private AnchorModule module;

    public AnchorCommand(Subsystem subsystem, ModularDrivetrain drivetrain){
        addRequirements(subsystem);
        this.drivetrain = drivetrain;

        module = drivetrain.getInstalledModule(AnchorModule.class);
        if (module == null){
            throw new GongolieriumException("The drivetrain does not have an anchor module installed.");
        }
    }

    @Override
    public void initialize() {
        module.holdPosition();
    }

    @Override
    public void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    public void end(boolean interrupted) {
        module.stopHoldingPosition();
    }

    @Override
    public boolean isFinished() {
        // This command will need to be cancelled to stop it from running
        return false;
    }


}