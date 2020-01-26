package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;

import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule implements DriveModule {

    private Encoder mLeftEncoder, mRightEncoder;
    private PID mLeftPID, mRightPID;
    private boolean mIsEnabled;

    private double lastLeftDistance, lastRightDistance;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be greater than or equal to 0)
     */
    public AnchorModule(Encoder leftEncoder, Encoder rightEncoder, double strength){
        this(leftEncoder, rightEncoder, new PID(strength, 0, 0));
    }

    public AnchorModule(Encoder leftEncoder, Encoder rightEncoder, PID pid){
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
        double left = mLeftPID.calculate(mLeftEncoder.getDistance(), lastLeftDistance);
        double right = mRightPID.calculate(mRightEncoder.getDistance(), lastRightDistance);

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

    public void setPID(PID pid) {
        if (pid == null) throw new IllegalArgumentException("PID must be non-null");
        mLeftPID = new PID(pid.getP(), pid.getI(), pid.getD());
        mRightPID = new PID(pid.getP(), pid.getI(), pid.getD());
    }
}