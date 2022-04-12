package com.thegongoliers.output.drivetrain;

import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will stabilize the drivetrain (rotation-wise) while attempting to drive straight
 */
@UsedInCompetition(team = "5112", year = "2020")
public class GyroStabilityModule implements DriveModule, Resettable {

    private static final double DEFAULT_TURN_THRESHOLD = 0.01;

    private Gyro mGyro;
    private double mStrength;
    private double mSettlingTime;
    private double mTurnThreshold;
    private Clock mClock;

    private double lastHeading;
    private double lastStopTime;

    /**
     * Default constructor
     * @param gyro the gyroscope which determines the robot's heading
     * @param strength the stabilizing strength (higher values may become unstable, recommended ~0.02. Values must be greater than or equal to 0)
     * @param settlingTime the amount of time to allow the drivetrain to settle after turn inputs stop before applying turn corrections
     */
    public GyroStabilityModule(Gyro gyro, double strength, double settlingTime){
        setGyro(gyro);
        setStrength(strength);
        setSettlingTime(settlingTime);
        setTurnThreshold(DEFAULT_TURN_THRESHOLD);
        setClock(new RobotClock());

        lastHeading = gyro.getAngle();
        lastStopTime = 0;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (isTurning(desiredSpeed)){
            updateDesiredHeading();
            updateStopTime();
        } else if (isSettling()){
            updateDesiredHeading();
        } else {
            return getCorrectedDriveSpeed(desiredSpeed);
        }

        return desiredSpeed;
    }

    public void setClock(Clock clock){
        if (clock == null) throw new IllegalArgumentException("Clock must be non-null");
        mClock = clock;
    }

    public void setGyro(Gyro gyro){
        if (gyro == null) throw new IllegalArgumentException("Gyro must be non-null");
        mGyro = gyro;
    }

    public void setSettlingTime(double settlingTime){
        if (settlingTime < 0) throw new IllegalArgumentException("Settling time must be non-negative");
        mSettlingTime = settlingTime;
    }

    public void setTurnThreshold(double turnThreshold){
        if (turnThreshold < 0) throw new IllegalArgumentException("Turn threshold must be non-negative");
        mTurnThreshold = turnThreshold;
    }

    public void setStrength(double strength){
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mStrength = strength;
    }
    
    /**
     * Resets the desired heading of the robot to match the gyro
     **/
    @Override
    public void reset() {
        updateDesiredHeading();   
    }

    private boolean isTurning(DriveSpeed speed) {
        return Math.abs(speed.turn()) > mTurnThreshold;
    }

    private void updateDesiredHeading() {
        lastHeading = mGyro.getAngle();
    }

    private void updateStopTime() {
        lastStopTime = mClock.getTime();
    }

    private boolean isSettling() {
        return mClock.getTime() - lastStopTime < mSettlingTime;
    }

    private DriveSpeed getCorrectedDriveSpeed(DriveSpeed desiredSpeed) {
        double forward = desiredSpeed.forward();
        double turn = calculateTurnCorrection();
        return DriveSpeed.fromArcade(forward, turn);
    }

    private double calculateTurnCorrection() {
        return mStrength * (lastHeading - mGyro.getAngle());
    }
}
