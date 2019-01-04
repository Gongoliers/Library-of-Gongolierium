package com.thegongoliers.pathFollowing;

import com.thegongoliers.input.odometry.Odometry;
import com.thegongoliers.output.interfaces.SmartDrivetrain;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SmartDriveTrainSubsystem extends Subsystem implements SmartDrivetrain {

    public double getCenterDistance(){
        return Odometry.getDistance(getLeftDistance(), getRightDistance());
    }

}
