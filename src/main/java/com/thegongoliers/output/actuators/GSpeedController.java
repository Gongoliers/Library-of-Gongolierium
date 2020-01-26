package com.thegongoliers.output.actuators;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.input.odometry.VelocitySensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class GSpeedController implements SpeedController {

    private SpeedController mSpeedController;
    private DistanceSensor mDistanceSensor;
    private VelocitySensor mVelocitySensor;
    private PID mDistancePID;
    private PID mVelocityPID;

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityPID the PID for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(SpeedController speedController, Encoder encoder, PID distancePID, PID velocityPID){
        this(speedController, encoder::getDistance, encoder::getRate, distancePID, velocityPID);
    }

    /**
     * A speed controller with added functionality
     * Potentiometers can not use velocity control!
     * @param speedController the underlying speed controller
     * @param potentiometer the potentiometer which senses the movement of the motor controlled by the speed controller
     * @param distancePID the PID for setting a distance
     */
    public GSpeedController(SpeedController speedController, Potentiometer potentiometer, PID distancePID){
        this(speedController, potentiometer::get, () -> 0.0, distancePID, new PID(0, 0, 0));
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param distanceSensor
     * @param velocitySensor
     * @param distancePID the PID for setting a distance
     * @param velocityPID the PID for setting a velocity
     */
    public GSpeedController(SpeedController speedController, DistanceSensor distanceSensor, VelocitySensor velocitySensor, PID distancePID, PID velocityPID){
        mSpeedController = speedController;
        mDistanceSensor = distanceSensor;
        mVelocitySensor = velocitySensor;
        mDistancePID = distancePID;
        mVelocityPID = velocityPID;
    }

    @Override
    public void pidWrite(double output) {
        mSpeedController.pidWrite(output);
    }

    @Override
    public void set(double speed) {
        mSpeedController.set(speed);
    }

    /**
     * @param velocity the velocity of the motor in encoder units / second
     */
    public void setVelocity(double velocity){
        set(mSpeedController.get() + mVelocityPID.calculate(getVelocity(), velocity));
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
        return mSpeedController.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        mSpeedController.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mSpeedController.getInverted();
    }

    @Override
    public void disable() {
        mSpeedController.disable();
    }

    @Override
    public void stopMotor() {
        mSpeedController.stopMotor();
    }

}