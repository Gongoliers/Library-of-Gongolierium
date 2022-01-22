package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.DistanceSensor;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule implements DriveModule {

    private DistanceSensor mLeftDistanceSupplier, mRightDistanceSupplier;
    private PID mLeftPID, mRightPID;
    private boolean mIsEnabled;

    private double lastLeftDistance, lastRightDistance;

    /**
     * Default constructor
     * @param leftEncoder the left distance sensor (encoder)
     * @param rightEncoder the right distance sensor (encoder)
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be greater than or equal to 0)
     */
    public AnchorModule(DistanceSensor leftEncoder, DistanceSensor rightEncoder, double strength){
        this(leftEncoder, rightEncoder, new PID(strength, 0, 0));
    }

    public AnchorModule(DistanceSensor leftEncoder, DistanceSensor rightEncoder, PID pid){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setPID(pid);
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

    @Override
    public boolean overridesUser() {
        return mIsEnabled;
    }

    public void holdPosition(){
        mIsEnabled = true;
    }

    public void stopHoldingPosition(){
        mIsEnabled = false;
    }

    private DriveSpeed anchor(){
        double left = mLeftPID.calculate(mLeftDistanceSupplier.getDistance(), lastLeftDistance);
        double right = mRightPID.calculate(mRightDistanceSupplier.getDistance(), lastRightDistance);

        return new DriveSpeed(left, right);
    }

    private boolean isAnchoring() {
        return mIsEnabled;
    }

    private void updateLastPosition() {
        lastLeftDistance = mLeftDistanceSupplier.getDistance();
        lastRightDistance = mRightDistanceSupplier.getDistance();
    }

    public void setLeftEncoder(DistanceSensor leftEncoder) {
        if (leftEncoder == null) throw new IllegalArgumentException("Left encoder must be non-null");
        mLeftDistanceSupplier = leftEncoder;
    }

    public void setRightEncoder(DistanceSensor rightEncoder) {
        if (rightEncoder == null) throw new IllegalArgumentException("Right encoder must be non-null");
        mRightDistanceSupplier = rightEncoder;
    }

    public void setPID(PID pid) {
        if (pid == null) throw new IllegalArgumentException("PID must be non-null");
        mLeftPID = new PID(pid.getP(), pid.getI(), pid.getD());
        mRightPID = new PID(pid.getP(), pid.getI(), pid.getD());
    }
}