package com.thegongoliers.output.subsystems;

import com.thegongoliers.input.operator.JoystickTransformer;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.filter.LowPassFilter;
import com.thegongoliers.math.filter.RateLimiter;
import com.thegongoliers.output.interfaces.DriveTrainInterface;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain6CIM extends Subsystem implements DriveTrainInterface {

    private Command defaultCommand;
    private DifferentialDrive differentialDrive;
    private RateLimiter speedRateLimit, turnRateLimit;
    private LowPassFilter speedNoiseFilter, turnNoiseFilter;
    private double maxSpeed, stopSpeedThreshold, maxTurn, stopTurnThreshold;

    /**
     * Creates a drivetrain with 6 CIM motors (as used by the Gongoliers).
     * @param left1 One of the left motors.
     * @param left2 One of the left motors.
     * @param left3 One of the left motors.
     * @param right1 One of the right motors.
     * @param right2 One of the right motors.
     * @param right3 One of the right motors.
     * @param defaultCommand The default command to run.
     */
    public Drivetrain6CIM(SpeedController left1, SpeedController left2, SpeedController left3, SpeedController right1, SpeedController right2, SpeedController right3, Command defaultCommand){
        this.defaultCommand = defaultCommand;
        SpeedController leftMotors = new SpeedControllerGroup(left1, left2, left3);
        SpeedController rightMotors = new SpeedControllerGroup(right1, right2, right3);
        differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
        speedRateLimit = new RateLimiter(Double.POSITIVE_INFINITY);
        turnRateLimit = new RateLimiter(Double.POSITIVE_INFINITY);
        speedNoiseFilter = new LowPassFilter(0.0);
        turnNoiseFilter = new LowPassFilter(0.0);
        maxSpeed = 1.0;
        stopSpeedThreshold = 0.0;
        maxTurn = 1.0;
        stopTurnThreshold = 0.0;
    }

    /**
     * Set the rate limit for the speed.
     * @param rateLimit The rate limit for the speed.
     */
    public void setSpeedRateLimit(double rateLimit){
        speedRateLimit.setMaxRate(rateLimit);
    }

    /**
     * Set the rate limit for turning.
     * @param rateLimit The rate limit for turning.
     */
    public void setTurnRateLimit(double rateLimit){
        turnRateLimit.setMaxRate(rateLimit);
    }

    /**
     * Set the value in which the robot should be stopped.
     * @param stopSpeedThreshold The stop threshold.
     */
    public void setSpeedStopThreshold(double stopSpeedThreshold){
        this.stopSpeedThreshold = stopSpeedThreshold;
    }

    /**
     * Set the value in which the robot should not be turning.
     * @param stopTurnThreshold The stop threshold.
     */
    public void setTurnStopThreshold(double stopTurnThreshold){
        this.stopTurnThreshold = stopTurnThreshold;
    }

    /**
     * Set the max speed of the robot.
     * @param maxSpeed The max speed.
     */
    public void setMaxSpeed(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    /**
     * Set the max turn speed of the robot.
     * @param maxTurn The max turn speed.
     */
    public void setMaxTurnSpeed(double maxTurn){
        this.maxTurn = maxTurn;
    }

    /**
     * Set the amount of noise reduction from 0 to 1. Values closer to 1 have less noise.
     * @param noiseReduction The noise reduction factor.
     */
    public void setNoiseReduction(double noiseReduction){
        speedNoiseFilter.setFilterCoefficient(noiseReduction);
        turnNoiseFilter.setFilterCoefficient(noiseReduction);
    }

    @Override
    public void forward(double speed) {
        differentialDrive.arcadeDrive(calculateSpeed(speed), 0);
    }

    @Override
    public void backward(double speed) {
        differentialDrive.arcadeDrive(-calculateSpeed(speed), 0);
    }

    @Override
    public void rotateLeft(double speed) {
        differentialDrive.arcadeDrive(0, -calculateTurnSpeed(speed));
    }

    @Override
    public void rotateRight(double speed) {
        differentialDrive.arcadeDrive(0, calculateTurnSpeed(speed));
    }

    @Override
    public void arcade(double speed, double rotation) {
        differentialDrive.arcadeDrive(calculateSpeed(speed), calculateTurnSpeed(rotation));
    }

    /**
     * Drive in arcade mode, but without any constraints or filters.
     * @param speed The speed from {-1, 1}. Positive is forward.
     * @param rotation The rotation from {-1, 1}. Positive is right/clockwise.
     */
    public void arcadeRaw(double speed, double rotation){
        differentialDrive.arcadeDrive(speed, rotation);
    }

    @Override
    public void tank(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }

    @Override
    public void stop() {
        differentialDrive.stopMotor();
    }

    private double calculateSpeed(double speed){
        double limited = speedRateLimit.filter(speed);
        double reducedNoise = speedNoiseFilter.filter(limited);
        double constrained = MathExt.toRange(reducedNoise, -maxSpeed, maxSpeed);
        return JoystickTransformer.deadzone(constrained, stopSpeedThreshold);
    }

    private double calculateTurnSpeed(double speed){
        double limited = turnRateLimit.filter(speed);
        double reducedNoise = turnNoiseFilter.filter(limited);
        double constrained = MathExt.toRange(reducedNoise, -maxTurn, maxTurn);
        return JoystickTransformer.deadzone(constrained, stopTurnThreshold);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(defaultCommand);
    }
}
