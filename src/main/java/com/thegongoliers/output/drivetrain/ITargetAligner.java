package com.thegongoliers.output.drivetrain;

public interface ITargetAligner {
    void align(double desiredHorizontalOffset, double desiredTargetArea);

    void stopAligning();

    boolean isAligning();
}
