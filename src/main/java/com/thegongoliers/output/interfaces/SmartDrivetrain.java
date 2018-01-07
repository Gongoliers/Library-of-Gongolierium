package com.thegongoliers.output.interfaces;

import com.thegongoliers.output.PID;

public interface SmartDrivetrain extends DriveTrainInterface {

    double getLeftDistance();

    double getRightDistance();

    void resetEncoders();

    double getAngle();

    void resetGyro();

    PID getDriveDistancePID();

    PID getRotateAnglePID();

}
