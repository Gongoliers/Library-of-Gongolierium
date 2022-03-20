package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowerModule;
import com.thegongoliers.paths.SimplePath;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class FollowPathCommand extends CommandBase {

    private ModularDrivetrain drivetrain;
    private PathFollowerModule module;
    private SimplePath path;

    public FollowPathCommand(Subsystem subsystem, ModularDrivetrain drivetrain, SimplePath path){
        addRequirements(subsystem);
        this.drivetrain = drivetrain;
        this.path = path;

        module = drivetrain.getInstalledModule(PathFollowerModule.class);
        if (module == null){
            throw new GongolieriumException("The drivetrain does not have a path following module installed.");
        }
    }

    @Override
    public void initialize() {
        module.startFollowingPath(path);
    }

    @Override
    public void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    public void end(boolean interrupted) {
        module.stopFollowingPath();
    }

    @Override
    public boolean isFinished() {
        return !module.isFollowingPath();
    }


}