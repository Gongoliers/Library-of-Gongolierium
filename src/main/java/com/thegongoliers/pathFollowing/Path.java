package com.thegongoliers.pathFollowing;

import com.thegongoliers.annotations.TestedBy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path that = (Path) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(drivetrain, that.drivetrain);
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                '}';
    }
}
