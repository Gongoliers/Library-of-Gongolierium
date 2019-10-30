package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;
import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which work to prevent wheel slip while driving straight
 */
public class TractionControlModule extends BaseDriveModule {

    /**
     * The left encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_LEFT_ENCODER = "left_encoder";

    /**
     * The right encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_RIGHT_ENCODER = "right_encoder";

    /**
     * The strength of the traction control
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The minimum difference in encoder rates to consider slipping
     * Type: double
     */
    public static final String VALUE_SLIP_THRESHOLD = "slip_threshold";


    /**
     * The name of the module
     */
    public static final String NAME = "Traction Control";

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the strength (higher values may become unstable, small values recommended. Values must be >= 0)
     */
    public TractionControlModule(Encoder leftEncoder, Encoder rightEncoder, double strength){
        super();
        values.put(VALUE_LEFT_ENCODER, leftEncoder);
        values.put(VALUE_RIGHT_ENCODER, rightEncoder);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_SLIP_THRESHOLD, 1.0);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        double slipThreshold = (double) getValue(VALUE_SLIP_THRESHOLD);
        Encoder leftEncoder = (Encoder) getValue(VALUE_LEFT_ENCODER);
        Encoder rightEncoder = (Encoder) getValue(VALUE_RIGHT_ENCODER);

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();


        if (GMath.approximately(right, left)){
            // Trying to drive straight
            double leftRate = Math.abs(leftEncoder.getRate());
            double rightRate = Math.abs(rightEncoder.getRate());

            double slipAmount = Math.abs(leftRate - rightRate);

            if (slipAmount >= slipThreshold){
                if (leftRate > rightRate){
                    // Left is slipping
                    left = decreaseSpeed(left, strength * slipAmount);
                } else {
                    // Right is slipping
                    right = decreaseSpeed(right, strength * slipAmount);
                }
            }
        }
        return new DriveSpeed(left, right);
    }

    private double decreaseSpeed(double current, double delta){
        if (current < 0){
            return Math.min(current + delta, 0);
        } else {
            return Math.max(current - delta, 0);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}