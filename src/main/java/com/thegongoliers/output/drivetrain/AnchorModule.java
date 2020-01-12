package com.thegongoliers.output.drivetrain;

import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule implements DriveModule {

    private Encoder mLeftEncoder, mRightEncoder;
    private double mStrength;
    private boolean mIsEnabled;

    private double lastLeftDistance, lastRightDistance;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be greater than or equal to 0)
     */
    public AnchorModule(Encoder leftEncoder, Encoder rightEncoder, double strength){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setStrength(strength);
        mIsEnabled = false;

        updateLastPosition();
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (isAnchoring()){
            return anchor();
        } 

        updateLastPosition();
        return desiredSpeed;
    }

    public void holdPosition(){
        mIsEnabled = true;
    }

    public void stopHoldingPosition(){
        mIsEnabled = false;
    }

    private DriveSpeed anchor(){
        double left = mStrength * (lastLeftDistance - mLeftEncoder.getDistance());
        double right = mStrength * (lastRightDistance - mRightEncoder.getDistance());

        return new DriveSpeed(left, right);
    }

    private boolean isAnchoring() {
        return mIsEnabled;
    }

    private void updateLastPosition() {
        lastLeftDistance = mLeftEncoder.getDistance();
        lastRightDistance = mRightEncoder.getDistance();
    }

    public void setLeftEncoder(Encoder leftEncoder) {
        if (leftEncoder == null) throw new IllegalArgumentException("Left encoder must be non-null");
        mLeftEncoder = leftEncoder;
    }

    public void setRightEncoder(Encoder rightEncoder) {
        if (rightEncoder == null) throw new IllegalArgumentException("Right encoder must be non-null");
        mRightEncoder = rightEncoder;
    }

    public void setStrength(double strength) {
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mStrength = strength;
    }
}