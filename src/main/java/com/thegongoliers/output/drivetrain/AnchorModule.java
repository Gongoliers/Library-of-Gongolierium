package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;
import com.thegongoliers.utils.Resettable;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule implements DriveModule, Resettable {

    private DistanceSensor mLeftDistanceSupplier, mRightDistanceSupplier;
    private MotionController mLeftController, mRightController;
    private boolean mIsEnabled;

    private double lastLeftDistance, lastRightDistance;

    /**
     * Default constructor
     * @param leftEncoder the left distance sensor (encoder)
     * @param rightEncoder the right distance sensor (encoder)
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be greater than or equal to 0)
     */
    public AnchorModule(DistanceSensor leftEncoder, DistanceSensor rightEncoder, double strength){
        this(leftEncoder, rightEncoder, new PIDController(strength, 0, 0));
    }

    public AnchorModule(DistanceSensor leftEncoder, DistanceSensor rightEncoder, MotionController controller){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setController(controller);
        mIsEnabled = false;

        updateLastPosition();
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (isAnchoring()){
            return anchor(deltaTime);
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

    private DriveSpeed anchor(double delta){
        mLeftController.setSetpoint(lastLeftDistance);
        mRightController.setSetpoint(lastRightDistance);
        double left = mLeftController.calculate(mLeftDistanceSupplier.getDistance(), delta);
        double right = mRightController.calculate(mRightDistanceSupplier.getDistance(), delta);

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

    public void setController(MotionController controller) {
        if (controller == null) throw new IllegalArgumentException("Controller must be non-null");
        mLeftController = controller.copy();
        mRightController = controller.copy();
    }

    @Override
    public void reset() {
        stopHoldingPosition();
    }
}