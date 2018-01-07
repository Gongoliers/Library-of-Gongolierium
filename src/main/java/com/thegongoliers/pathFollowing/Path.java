package com.thegongoliers.pathFollowing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Path implements Iterable<PathTaskCommand> {

    private List<PathTaskCommand> path;
    private SmartDriveTrainSubsystem drivetrain;

    public Path(SmartDriveTrainSubsystem drivetrain) {
        path = new LinkedList<>();
        this.drivetrain = drivetrain;
    }

    public void addStraightAway(double distance) {
        path.add(new PathStraightAwayCommand(drivetrain, distance));
    }

    public void addRotation(double degrees) {
        path.add(new PathRotateCommand(drivetrain, degrees));
    }

    @Override
    public Iterator<PathTaskCommand> iterator() {
        return path.iterator();
    }

}
