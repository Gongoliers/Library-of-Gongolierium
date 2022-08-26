package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class FieldOrientedSwerveModule implements IModule<SwerveSpeed>, Resettable {

    private Gyro mGyro;
    private double lastHeading;

    public FieldOrientedSwerveModule(Gyro gyro) {
        mGyro = gyro;
        lastHeading = gyro.getAngle();
    }

    @Override
    public SwerveSpeed run(SwerveSpeed current, SwerveSpeed desired, double deltaTime) {
        var heading = getHeadingRadians();
        var x = desired.getX() * Math.cos(heading) + desired.getY() * Math.sin(heading);
        var y = -desired.getX() * Math.sin(heading) + desired.getY() * Math.cos(heading);
        return new SwerveSpeed(x, y, desired.getRotation());
    }

    private double getHeadingRadians() {
        return Math.toRadians(mGyro.getAngle() - lastHeading);
    }

    @Override
    public void reset() {
        lastHeading = mGyro.getAngle();
    }
}
