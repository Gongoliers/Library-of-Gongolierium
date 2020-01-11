package com.thegongoliers.output.drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule implements DriveModule {

    /**
     * The name of the module
     */
    public static final String NAME = "Anchor";

    private Encoder mLeftEncoder, mRightEncoder;
    private double mStrength;
    private Trigger mTrigger;

    private double lastLeftDistance, lastRightDistance;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will lock the drivetrain in place
     */
    public AnchorModule(Encoder leftEncoder, Encoder rightEncoder, double strength, Trigger trigger){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setTrigger(trigger);
        setStrength(strength);

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

    private DriveSpeed anchor(){
        double left = mStrength * (lastLeftDistance - mLeftEncoder.getDistance());
        double right = mStrength * (lastRightDistance - mRightEncoder.getDistance());

        return new DriveSpeed(left, right);
    }

    private boolean isAnchoring() {
        return mTrigger.get();
    }

    private void updateLastPosition() {
        lastLeftDistance = mLeftEncoder.getDistance();
        lastRightDistance = mRightEncoder.getDistance();
    }

    @Override
    public String getName() {
        return NAME;
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

    public void setTrigger(Trigger trigger) {
        if (trigger == null) throw new IllegalArgumentException("Trigger must be non-null");
        mTrigger = trigger;
    }
}