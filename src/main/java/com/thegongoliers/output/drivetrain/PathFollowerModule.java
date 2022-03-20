package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;
import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.input.odometry.EncoderSensor;
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

    private static final double DEFAULT_FORWARD_TOLERANCE = 0.1;
    private static final double DEFAULT_TURN_TOLERANCE = 0.1;

    private Gyro mGyro;
    private EncoderSensor mEncoder;
    private PID mForwardPID;
    private PID mTurnPID;
    private SimplePath mPath;

    private boolean isEnabled;

    private SimplePath currentPath;
    private int currentStepIdx;

    private double distanceZero;
    private double angleZero;


    public PathFollowerModule(Gyro gyro, EncoderSensor encoder, double forwardStrength, double turnStrength){
        this(gyro, encoder, new PID(forwardStrength, 0, 0), new PID(turnStrength, 0, 0));
        setTurnTolerance(DEFAULT_TURN_TOLERANCE);
        setForwardTolerance(DEFAULT_FORWARD_TOLERANCE);
    }

    public PathFollowerModule(Gyro gyro, EncoderSensor encoder, PID forwardPID, PID turnPID){
        super();
        setGyro(gyro);
        setEncoder(encoder);
        setForwardPID(forwardPID);
        setTurnPID(turnPID);
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

    @Override
    public boolean overridesUser() {
        return isFollowingPath();
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

    public void setEncoder(EncoderSensor encoder){
        mEncoder = encoder;
    }

    public void setGyro(Gyro gyro){
        if (gyro == null) throw new IllegalArgumentException("Gyro must be non-null");
        mGyro = gyro;
    }

    public void setForwardTolerance(double tolerance){
        if (tolerance < 0) throw new IllegalArgumentException("Tolerance must be non-negative");
        mForwardPID.setPositionTolerance(tolerance);
    }

    public void setTurnTolerance(double tolerance){
        if (tolerance < 0) throw new IllegalArgumentException("Tolerance must be non-negative");
        mTurnPID.setPositionTolerance(tolerance);
    }

    public void setForwardPID(PID forwardPID){
        if (forwardPID == null) throw new IllegalArgumentException("PID must be non-null");
        mForwardPID = forwardPID;
    }

    public void setTurnPID(PID turnPID){
        if (turnPID == null) throw new IllegalArgumentException("PID must be non-null");
        mTurnPID = turnPID;
    }

    private boolean hasPathToFollow() {
        return mPath != null;
    }

    private boolean hasNotStartedPath() {
        return currentPath == null;
    }

    private void startFollowingPath() {
        mForwardPID.reset();
        mTurnPID.reset();
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
                mTurnPID.reset();
                return getToNextStep();
            }
            return speed;
        } else {
            DriveSpeed speed = driveTowards(step.getValue());
            if (isDoneDriving()){
                mForwardPID.reset();
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
        return DriveSpeed.fromArcade(0, mTurnPID.calculate(getAngle(), angle));
    }

    private DriveSpeed driveTowards(double distance){
        var speed = mForwardPID.calculate(getDistance(), distance);
        return new DriveSpeed(speed, speed);
    }

    private boolean isDoneRotating(){
        return mTurnPID.atSetpoint();
    }

    private boolean isDoneDriving(){
        return mForwardPID.atSetpoint();
    }

    private double getDistance(){
        return mEncoder.getDistance() - distanceZero;
    }

    private double getAngle(){
        return mGyro.getAngle() - angleZero;
    }

    private void zeroSensors(){
        distanceZero = mEncoder.getDistance();
        angleZero = mGyro.getAngle();
    }

    @Override
    public void reset() {
        stopFollowingPath();
    }
}