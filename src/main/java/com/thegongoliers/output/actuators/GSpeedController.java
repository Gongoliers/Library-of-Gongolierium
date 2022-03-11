package com.thegongoliers.output.actuators;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.odometry.VelocitySensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.math.GMath;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class GSpeedController implements MotorController {

    private MotorController mSpeedController;
    private DistanceSensor mDistanceSensor;
    private VelocitySensor mVelocitySensor;
    private PID mDistancePID;
    private PID mVelocityPID;
    private double mSecondsToFullSpeed;
    private Clock mClock;
    private double mLastTime;
    private double mScale = 1.0;

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     */
    public GSpeedController(MotorController speedController, Clock clock){
        this(speedController, () -> 0.0, () -> 0.0, new PID(0, 0, 0), new PID(0, 0, 0), clock);
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     */
    public GSpeedController(MotorController speedController){
        this(speedController, new RobotClock());
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityPID the PID for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(MotorController speedController, Encoder encoder, PID distancePID, PID velocityPID, Clock clock){
        this(speedController, encoder::getDistance, encoder::getRate, distancePID, velocityPID, clock);
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityPID the PID for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(MotorController speedController, Encoder encoder, PID distancePID, PID velocityPID){
        this(speedController, encoder::getDistance, encoder::getRate, distancePID, velocityPID, new RobotClock());
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityPID the PID for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(MotorController speedController, EncoderSensor encoder, PID distancePID, PID velocityPID, Clock clock){
        this(speedController, encoder::getDistance, encoder::getVelocity, distancePID, velocityPID, clock);
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityPID the PID for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(MotorController speedController, EncoderSensor encoder, PID distancePID, PID velocityPID){
        this(speedController, encoder::getDistance, encoder::getVelocity, distancePID, velocityPID, new RobotClock());
    }

    /**
     * A speed controller with added functionality
     * Potentiometers can not use velocity control!
     * @param speedController the underlying speed controller
     * @param potentiometer the potentiometer which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance
     */
    public GSpeedController(MotorController speedController, AnalogPotentiometer potentiometer, PID distancePID, Clock clock){
        this(speedController, potentiometer::get, () -> 0.0, distancePID, new PID(0, 0, 0), clock);
    }

    /**
     * A speed controller with added functionality
     * Potentiometers can not use velocity control!
     * @param speedController the underlying speed controller
     * @param potentiometer the potentiometer which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance
     */
    public GSpeedController(MotorController speedController, AnalogPotentiometer potentiometer, PID distancePID){
        this(speedController, potentiometer::get, () -> 0.0, distancePID, new PID(0, 0, 0), new RobotClock());
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param distanceSensor
     * @param velocitySensor
     * @param distancePID the PID for setting a distance
     * @param velocityPID the PID for setting a velocity
     */
    public GSpeedController(MotorController speedController, DistanceSensor distanceSensor, VelocitySensor velocitySensor, PID distancePID, PID velocityPID){
        this(speedController, distanceSensor, velocitySensor, distancePID, velocityPID, new RobotClock());
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param distanceSensor
     * @param velocitySensor
     * @param distancePID the PID for setting a distance
     * @param velocityPID the PID for setting a velocity
     * @param clock the clock
     */
    public GSpeedController(MotorController speedController, DistanceSensor distanceSensor, VelocitySensor velocitySensor, PID distancePID, PID velocityPID, Clock clock){
        mSpeedController = speedController;
        mDistanceSensor = distanceSensor;
        mVelocitySensor = velocitySensor;
        mDistancePID = distancePID;
        mVelocityPID = velocityPID;
        mClock = clock;
        mLastTime = mClock.getTime();
    }

    @Override
    public void set(double speed) {
        if (mSecondsToFullSpeed <= 0){
            mSpeedController.set(speed * mScale);
        } else {
            var time = mClock.getTime();
            var deltaTime = time - mLastTime;
            mLastTime = time;
            double maximumRate = getMaxRate(deltaTime);
            var newSpeed = GMath.rateLimit(maximumRate, speed, get());
            mSpeedController.set(newSpeed * mScale);
        }
    }

    /**
     * Controls the ramping of the motor. Set to 0 to disable.
     * @param secondsToFullSpeed the ramping time in seconds from 0 to full speed
     */
    public void setSecondsToFullSpeed(double secondsToFullSpeed){
        mSecondsToFullSpeed = Math.abs(secondsToFullSpeed);
    }

    /**
     * @param velocity the velocity of the motor in encoder units / second
     */
    public void setVelocity(double velocity){
        set(get() + mVelocityPID.calculate(getVelocity(), velocity));
    }

    /**
     * @return the velocity of the motor in encoder units / second
     */
    public double getVelocity(){
        return mVelocitySensor.getVelocity();
    }

    /**
     * @param distance the distance of the motor in encoder units
     */
    public void setDistance(double distance){
        set(mDistancePID.calculate(getDistance(), distance));
    }

    /**
     * @return the distance of the motor in encoder units
     */
    public double getDistance(){
        return mDistanceSensor.getDistance();
    }

    @Override
    public double get() {
        return mSpeedController.get() / mScale;
    }

    @Override
    public void setInverted(boolean isInverted) {
        mSpeedController.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mSpeedController.getInverted();
    }

    public void setScale(double scale){
        mScale = scale;
    }

    @Override
    public void disable() {
        mSpeedController.disable();
    }

    @Override
    public void stopMotor() {
        mSpeedController.stopMotor();
    }

    private double getMaxRate(double deltaTime) {
        return mSecondsToFullSpeed == 0 ? 1 : deltaTime / mSecondsToFullSpeed;
    }

}