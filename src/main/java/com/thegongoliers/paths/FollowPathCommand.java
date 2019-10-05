package com.thegongoliers.paths;

import java.util.List;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.DriveModule;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowingModule;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FollowPathCommand extends Command {

    private ModularDrivetrain drivetrain;
    private DriveModule module;
    private SimplePath path;

    public FollowPathCommand(Subsystem subsystem, ModularDrivetrain drivetrain, SimplePath path){
        requires(subsystem);
        this.drivetrain = drivetrain;
        this.path = path;

        List<DriveModule> modules = drivetrain.getInstalledModules();
        for (DriveModule module : modules) {
            if (module.getName().equals(PathFollowingModule.NAME)){
                this.module = module;
                break;
            }
        }

        if (module == null){
            throw new GongolieriumException("The drivetrain does not have a path following module installed!");
        }
    }

    @Override
    protected void initialize() {
        module.setValue(PathFollowingModule.VALUE_PATH, path);
        module.setValue(PathFollowingModule.VALUE_TRIGGER, true);
    }

    @Override
    protected void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    protected void end() {
        module.setValue(PathFollowingModule.VALUE_TRIGGER, false);
    }

    @Override
    protected boolean isFinished() {
        return !((boolean) module.getValue(PathFollowingModule.VALUE_TRIGGER));
    }


}