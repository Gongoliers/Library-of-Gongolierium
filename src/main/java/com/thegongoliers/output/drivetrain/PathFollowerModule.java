package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;
import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.paths.PathStep;
import com.thegongoliers.paths.PathStepType;
import com.thegongoliers.paths.SimplePath;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will follow a path when activated
 */
@UsedInCompetition(team = "5112", year = "2020")
public class PathFollowerModule implements DriveModule, Resettable {

    private Gyro mGyro;
    private EncoderSensor mEncoder;
    private MotionController mForwardController;
    private MotionController mTurnController;
    private SimplePath mPath;

    private boolean isEnabled;

    private SimplePath currentPath;
    private int currentStepIdx;

    private double distanceZero;
    private double angleZero;


    public PathFollowerModule(Gyro gyro, EncoderSensor encoder, MotionController forwardController, MotionController turnController) {
        super();
        setGyro(gyro);
        setEncoder(encoder);
        setForwardController(forwardController);
        setTurnController(turnController);
        mPath = null;
        stopFollowingPath();
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (!isEnabled || !hasPathToFollow()) return desiredSpeed;

        if (hasNotStartedPath()) {
            startFollowingPath();
        }

        if (isDoneFollowingPath()) {
            stopFollowingPath();
            return DriveSpeed.STOP;
        }

        return followPath(deltaTime);
    }

    @Override
    public boolean overridesUser() {
        return isFollowingPath();
    }

    /**
     * Start following a path (note, drivetrain's tank or arcade methods must be called repeatedly with any value)
     *
     * @param path The path to follow
     */
    public void startFollowingPath(SimplePath path) {
        mPath = path;
        isEnabled = true;
        currentStepIdx = 0;
        currentPath = null;
        zeroSensors();
    }

    /**
     * Determines if it is following a path
     */
    public boolean isFollowingPath() {
        return isEnabled && hasPathToFollow();
    }

    /**
     * Stops following a path - the drivetrain's arcade or tank method must be called to take effect!
     */
    public void stopFollowingPath() {
        isEnabled = false;
        currentPath = null;
        currentStepIdx = 0;
    }

    public void setEncoder(EncoderSensor encoder) {
        mEncoder = encoder;
    }

    public void setGyro(Gyro gyro) {
        if (gyro == null) throw new IllegalArgumentException("Gyro must be non-null");
        mGyro = gyro;
    }

    public void setForwardController(MotionController forwardController) {
        if (forwardController == null) throw new IllegalArgumentException("Controller must be non-null");
        mForwardController = forwardController;
    }

    public void setTurnController(MotionController turnController) {
        if (turnController == null) throw new IllegalArgumentException("Controller must be non-null");
        mTurnController = turnController;
    }

    private boolean hasPathToFollow() {
        return mPath != null;
    }

    private boolean hasNotStartedPath() {
        return currentPath == null;
    }

    private void startFollowingPath() {
        mForwardController.reset();
        mTurnController.reset();
        currentPath = mPath;
        currentStepIdx = 0;
        zeroSensors();
    }

    private boolean isDoneFollowingPath() {
        return currentPath.getSteps().size() <= currentStepIdx;
    }

    private DriveSpeed followPath(double delta) {
        PathStep step = getCurrentPathStep();

        if (step.getType() == PathStepType.ROTATION) {
            DriveSpeed speed = rotateTowards(step.getValue(), delta);
            if (isDoneRotating()) {
                mTurnController.reset();
                return getToNextStep();
            }
            return speed;
        } else {
            DriveSpeed speed = driveTowards(step.getValue(), delta);
            if (isDoneDriving()) {
                mForwardController.reset();
                return getToNextStep();
            }
            return speed;
        }
    }

    private PathStep getCurrentPathStep() {
        return currentPath.getSteps().get(currentStepIdx);
    }

    private DriveSpeed getToNextStep() {
        currentStepIdx++;
        zeroSensors();
        return DriveSpeed.STOP;
    }

    private DriveSpeed rotateTowards(double angle, double delta) {
        mTurnController.setSetpoint(angle);
        return DriveSpeed.fromArcade(0, mTurnController.calculate(getAngle(), delta));
    }

    private DriveSpeed driveTowards(double distance, double delta) {
        mForwardController.setSetpoint(distance);
        var speed = mForwardController.calculate(getDistance(), delta);
        return new DriveSpeed(speed, speed);
    }

    private boolean isDoneRotating() {
        return mTurnController.atSetpoint();
    }

    private boolean isDoneDriving() {
        return mForwardController.atSetpoint();
    }

    private double getDistance() {
        return mEncoder.getDistance() - distanceZero;
    }

    private double getAngle() {
        return mGyro.getAngle() - angleZero;
    }

    private void zeroSensors() {
        distanceZero = mEncoder.getDistance();
        angleZero = mGyro.getAngle();
    }

    @Override
    public void reset() {
        stopFollowingPath();
    }
}