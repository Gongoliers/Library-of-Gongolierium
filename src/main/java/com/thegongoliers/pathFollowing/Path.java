package com.thegongoliers.pathFollowing;

import com.thegongoliers.annotations.TestedBy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@TestedBy(team = "5112", year = "2018")
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

    public void addStraightAway(double distance, double timeout) {
        path.add(new PathStraightAwayCommand(drivetrain, distance, timeout));
    }

    public void addRotation(double degrees, double timeout) {
        path.add(new PathRotateCommand(drivetrain, degrees, timeout));
    }

    @Override
    public Iterator<PathTaskCommand> iterator() {
        return path.iterator();
    }

}
