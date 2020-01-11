package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;
import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which work to prevent wheel slip while driving straight
 */
public class TractionControlModule implements DriveModule {

    private static final double DEFAULT_TURNING_THRESHOLD = 0;

    /**
     * The name of the module
     */
    public static final String NAME = "Traction Control";

    private Encoder mLeftEncoder, mRightEncoder;
    private double mStrength;
    private double mSlipThreshold;
    private double mTurningThreshold;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param slipThreshold the maximum slip ratio to consider as slipping
     */
    public TractionControlModule(Encoder leftEncoder, Encoder rightEncoder, double strength, double slipThreshold){
        super();
        mLeftEncoder = leftEncoder;
        mRightEncoder = rightEncoder;
        mStrength = strength;
        mSlipThreshold = slipThreshold;
        mTurningThreshold = DEFAULT_TURNING_THRESHOLD;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (!isMoving()) return desiredSpeed;

        if (shouldBeDrivingStraight(desiredSpeed) && isSlipping()){
            return preventSlipping(desiredSpeed);
        }
        return desiredSpeed;
    }

    private DriveSpeed preventSlipping(DriveSpeed speed){
        double leftRate = Math.abs(mLeftEncoder.getRate());
        double rightRate = Math.abs(mRightEncoder.getRate());
        double slipRatio = calculateSlipRatio();

        double left = speed.getLeftSpeed();
        double right = speed.getRightSpeed();

        if (leftRate > rightRate){
            // Left is slipping
            left = decreaseSpeed(left, mStrength * slipRatio);
        } else {
            // Right is slipping
            right = decreaseSpeed(right, mStrength * slipRatio);
        }

        return new DriveSpeed(left, right);
    }
    
    private boolean isSlipping() {
        return calculateSlipRatio() >= mSlipThreshold;
    }

    private double calculateSlipRatio() {
        return Math.abs(getMaxSpeed() - getMinSpeed()) / getMinSpeed();
    }

    private double getMinSpeed() {
        double leftRate = Math.abs(mLeftEncoder.getRate());
        double rightRate = Math.abs(mRightEncoder.getRate());

        return Math.min(leftRate, rightRate);
    }

    private double getMaxSpeed() {
        double leftRate = Math.abs(mLeftEncoder.getRate());
        double rightRate = Math.abs(mRightEncoder.getRate());

        return Math.max(leftRate, rightRate);
    }

    private boolean isMoving(){
        boolean encodersStopped = mLeftEncoder.getStopped() && mRightEncoder.getStopped();
        return !encodersStopped && !GMath.approximately(getMinSpeed(), 0);
    }

    private boolean shouldBeDrivingStraight(DriveSpeed speed) {
        return Math.abs(speed.getLeftSpeed() - speed.getRightSpeed()) <= mTurningThreshold;
    }

    private double decreaseSpeed(double current, double delta) {
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