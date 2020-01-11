package com.thegongoliers.paths;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowerModule;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FollowPathCommand extends Command {

    private ModularDrivetrain drivetrain;
    private PathFollowerModule module;
    private SimplePath path;

    public FollowPathCommand(Subsystem subsystem, ModularDrivetrain drivetrain, SimplePath path){
        requires(subsystem);
        this.drivetrain = drivetrain;
        this.path = path;

        PathFollowerModule module = drivetrain.getInstalledModule(PathFollowerModule.class);
        if (module == null){
            throw new GongolieriumException("The drivetrain does not have a path following module installed.");
        }
    }

    @Override
    protected void initialize() {
        module.startFollowingPath(path);
    }

    @Override
    protected void execute() {
        drivetrain.arcade(0, 0); // Causes the module to run, ignores input values
    }

    @Override
    protected void end() {
        module.stopFollowingPath();
    }

    @Override
    protected boolean isFinished() {
        return !module.isFollowingPath();
    }


}