package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will stabilize the drivetrain (rotation-wise) while attempting to drive straight
 */
public class StabilityModule extends BaseDriveModule {

    /**
     * The gyroscope which determines the robot's heading
     * Type: edu.wpi.first.wpilibj.interfaces.Gyro
     */
    public static final String VALUE_GYRO = "gyro";

    /**
     * The stabilizing strength (higher values may become unstable, recommended ~0.02. Values must be >= 0).
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The amount of time to allow the drivetrain to settle after turn inputs stop before applying turn corrections.
     * Type: double
     */
    public static final String VALUE_SETTLING_TIME = "settling_time";

    /**
     * The input threshold for turning to activate the stability module (between 0 and 1, defaults to 0)
     * Type: double
     */
    public static final String VALUE_THRESHOLD = "threshold";

    /**
     * The clock to use to calculate the settling time (defaults to the robot's clock).
     * Type: com.thegongoliers.input.time.Clock
     */
    public static final String VALUE_CLOCK = "clock";

    /**
     * The name of the module
     */
    public static final String NAME = "Stability";

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
        values.put(VALUE_THRESHOLD, 0.01);
        values.put(VALUE_CLOCK, new RobotClock());

        lastHeading = gyro.getAngle();
        lastStopTime = 0;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        double settlingTime = (double) getValue(VALUE_SETTLING_TIME);
        double threshold = (double) getValue(VALUE_THRESHOLD);
        Clock clock = (Clock) getValue(VALUE_CLOCK);

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();

        if (Math.abs(left - right) > threshold){
            // Turning
            lastHeading = gyro.getAngle();
            lastStopTime = clock.getTime();
        } else {
            // Driving straight
            if (clock.getTime() - lastStopTime < settlingTime){
                lastHeading = gyro.getAngle();
            }
            double forward = (left + right) / 2;
            double turn = strength * (lastHeading - gyro.getAngle());
            return DriveSpeed.fromArcade(forward, turn);
        }

        return new DriveSpeed(left, right);
    }

    @Override
    public String getName() {
        return NAME;
    }

}