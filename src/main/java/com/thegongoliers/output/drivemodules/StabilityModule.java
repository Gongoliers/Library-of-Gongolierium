package com.thegongoliers.output.drivemodules;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will stabilize the drivetrain (rotation-wise) while attempting to drive straight
 */
public class StabilityModule extends BaseDriveModule {

    public static final String VALUE_GYRO = "gyro";
    public static final String VALUE_STRENGTH = "strength";
    public static final String VALUE_SETTLING_TIME = "settling_time";
    public static final String VALUE_THRESHOLD = "threshold";

    private double lastHeading;
    private double lastStopTime;

    /**
     * Default constructor
     * @param gyro the gyroscope which determines the robot's heading
     * @param strength the stabilizing strength (higher values may become unstable, recommended ~0.02. Values must be >= 0)
     * @param settlingTime the amount of time to allow the drivetrain to settle after turn inputs stop before applying turn corrections
     */
    public StabilityModule(Gyro gyro, double strength, double settlingTime){
        super();
        values.put(VALUE_GYRO, gyro);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_SETTLING_TIME, settlingTime);
        values.put(VALUE_THRESHOLD, 0);

        lastHeading = gyro.getAngle();
        lastStopTime = 0;
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        double settlingTime = (double) getValue(VALUE_SETTLING_TIME);
        double threshold = (double) getValue(VALUE_THRESHOLD);

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        if (Math.abs(turnSpeed) > threshold){
            lastHeading = gyro.getAngle();
            lastStopTime = Timer.getFPGATimestamp();
        } else {
            if (Timer.getFPGATimestamp() - lastStopTime < settlingTime){
                lastHeading = gyro.getAngle();
            }
            turnSpeed = -strength * (gyro.getAngle() - lastHeading);
        }

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}