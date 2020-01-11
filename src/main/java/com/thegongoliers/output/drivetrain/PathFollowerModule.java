package com.thegongoliers.output.drivetrain;

import java.util.List;
import java.util.OptionalDouble;

import com.thegongoliers.paths.SimplePath;
import com.thegongoliers.paths.PathStep;
import com.thegongoliers.paths.PathStepType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will follow a path when activated
 */
public class PathFollowerModule implements DriveModule {

    /**
     * The name of the module
     */
    public static final String NAME = "Path Follower";

    private Gyro mGyro;
    private List<Encoder> mEncoders;
    private PIDController mForwardController, mTurnController;
    private SimplePath mPath;

    private boolean isEnabled;

    private SimplePath currentPath;
    private int currentStepIdx;


    public PathFollowerModule(Gyro gyro, List<Encoder> encoders, PIDController forwardController, PIDController turnController){
        super();
        mGyro = gyro;
        mEncoders = encoders;
        mForwardController = forwardController;
        mTurnController = turnController;
        mPath = null;
        stopFollowingPath();
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime){
        if (!isEnabled) return desiredSpeed;
        if (!hasPathToFollow()) return desiredSpeed;

        if (hasNotStartedPath()){
            startFollowingPath();
        }

        if (isDoneFollowingPath()){
            stopFollowingPath();
            return DriveSpeed.STOP;
        }

        return followPath();
    }

    /**
     * Start following a path (note, drivetrain's tank or arcade methods must be called repeatedly with any value)
     * @param path The path to follow
     */
    public void startFollowingPath(SimplePath path){
        mPath = path;
        isEnabled = true;
    }

    /**
     * Determines if it is following a path
     */
    public boolean isFollowingPath(){
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

    private boolean hasPathToFollow() {
        return mPath == null;
    }

    private boolean hasNotStartedPath() {
        return currentPath == null;
    }

    private void startFollowingPath() {
        currentPath = mPath;
        currentStepIdx = 0;
        zeroSensors();
    }

    private boolean isDoneFollowingPath() {
        return currentPath.getSteps().size() <= currentStepIdx;
    }

    private DriveSpeed followPath(){
        PathStep step = getCurrentPathStep();

        if (step.getType() == PathStepType.ROTATION){
            DriveSpeed speed = rotateTowards(step.getValue());
            if (isDoneRotating()){
                return getToNextStep();
            }
            return speed;
        } else {
            DriveSpeed speed = driveTowards(step.getValue());
            if (isDoneDriving()){
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

    private DriveSpeed rotateTowards(double angle){
        double turnSpeed = mTurnController.calculate(mGyro.getAngle(), angle);
        return DriveSpeed.fromArcade(0, turnSpeed);
    }

    private DriveSpeed driveTowards(double distance){
        double speed = mForwardController.calculate(getDistance(), distance);
        return new DriveSpeed(speed, speed);
    }

    private boolean isDoneRotating(){
        return mTurnController.atSetpoint();
    }

    private boolean isDoneDriving(){
        return mForwardController.atSetpoint();  
    }

    private double getDistance(){
        OptionalDouble average = mEncoders.stream().mapToDouble(Encoder::getDistance).average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    private void zeroSensors(){
        mEncoders.forEach(Encoder::reset);
        mGyro.reset();
    }

    @Override
    public String getName() {
        return NAME;
    }
}