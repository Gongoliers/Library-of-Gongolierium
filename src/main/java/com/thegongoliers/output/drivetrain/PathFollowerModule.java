package com.thegongoliers.output.drivetrain;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import com.thegongoliers.paths.SimplePath;
import com.thegongoliers.paths.PathStep;
import com.thegongoliers.paths.PathStepType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will follow a path when activated
 */
public class PathFollowerModule implements DriveModule {

    private static final double DEFAULT_FORWARD_TOLERANCE = 0.1;
    private static final double DEFAULT_TURN_TOLERANCE = 0.1;

    private Gyro mGyro;
    private List<Encoder> mEncoders;
    private double mForwardStrength, mTurnStrength;
    private double mForwardTolerance, mTurnTolerance;
    private SimplePath mPath;

    private boolean isEnabled;

    private SimplePath currentPath;
    private int currentStepIdx;


    public PathFollowerModule(Gyro gyro, List<Encoder> encoders, double forwardStrength, double turnStrength){
        super();
        setGyro(gyro);
        setEncoders(encoders);
        setForwardStrength(forwardStrength);
        setTurnStrength(turnStrength);
        setForwardTolerance(DEFAULT_FORWARD_TOLERANCE);
        setTurnTolerance(DEFAULT_TURN_TOLERANCE);
        mPath = null;
        stopFollowingPath();
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime){
        if (!isEnabled || !hasPathToFollow()) return desiredSpeed;

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
        currentStepIdx = 0;
        currentPath = null;
        zeroSensors();
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

    public void setEncoders(List<Encoder> encoders){
        if (encoders == null || encoders.isEmpty()) throw new IllegalArgumentException("At least one encoder must be supplied");
        if (encoders.parallelStream().anyMatch(e -> e == null)) throw new IllegalArgumentException("All encoders must be non-null");
        mEncoders = encoders.stream().collect(Collectors.toList());
    }

    public void setGyro(Gyro gyro){
        if (gyro == null) throw new IllegalArgumentException("Gyro must be non-null");
        mGyro = gyro;
    }

    public void setForwardTolerance(double tolerance){
        if (tolerance < 0) throw new IllegalArgumentException("Tolerance must be non-negative");
        mForwardTolerance = tolerance;
    }

    public void setTurnTolerance(double tolerance){
        if (tolerance < 0) throw new IllegalArgumentException("Tolerance must be non-negative");
        mTurnTolerance = tolerance;
    }

    public void setForwardStrength(double strength){
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mForwardStrength = strength;
    }

    public void setTurnStrength(double strength){
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mTurnStrength = strength;
    }

    private boolean hasPathToFollow() {
        return mPath != null;
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
            if (isDoneRotating(step.getValue())){
                return getToNextStep();
            }
            return speed;
        } else {
            DriveSpeed speed = driveTowards(step.getValue());
            if (isDoneDriving(step.getValue())){
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
        var error = angle - mGyro.getAngle();
        var turnSpeed = error * mTurnStrength;
        return DriveSpeed.fromArcade(0, turnSpeed);
    }

    private DriveSpeed driveTowards(double distance){
        var error = distance - getDistance();
        var speed = error * mForwardStrength;
        return new DriveSpeed(speed, speed);
    }

    private boolean isDoneRotating(double angle){
        return Math.abs(angle - mGyro.getAngle()) <= mTurnTolerance;
    }

    private boolean isDoneDriving(double distance){
        return Math.abs(distance - getDistance()) <= mForwardTolerance;
    }

    private double getDistance(){
        OptionalDouble average = mEncoders.stream().mapToDouble(Encoder::getDistance).average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    private void zeroSensors(){
        mEncoders.forEach(Encoder::reset);
        mGyro.reset();
    }
}