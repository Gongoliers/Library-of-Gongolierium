package com.thegongoliers.output.actuators;

import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.input.odometry.VelocitySensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class GSpeedController implements SpeedController {

    private SpeedController mSpeedController;
    private DistanceSensor mDistanceSensor;
    private VelocitySensor mVelocitySensor;
    private double mDistanceCorrectionStrength, mVelocityCorrectionStrength;

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param encoder the encoder which senses the movement of the motor controlled by the speed controller
     * @param distanceCorrectionStrength the correction strength for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityCorrectionStrength the correction strength for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(SpeedController speedController, Encoder encoder, double distanceCorrectionStrength, double velocityCorrectionStrength){
        this(speedController, encoder::getDistance, encoder::getRate, distanceCorrectionStrength, velocityCorrectionStrength);
    }

    /**
     * A speed controller with added functionality
     * Potentiometers can not use velocity control!
     * @param speedController the underlying speed controller
     * @param potentiometer the potentiometer which senses the movement of the motor controlled by the speed controller
     * @param distanceCorrectionStrength the correction strength for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityCorrectionStrength the correction strength for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(SpeedController speedController, Potentiometer potentiometer, double distanceCorrectionStrength, double velocityCorrectionStrength){
        this(speedController, potentiometer::get, () -> 0.0, distanceCorrectionStrength, velocityCorrectionStrength);
    }

    /**
     * A speed controller with added functionality
     * @param speedController the underlying speed controller
     * @param distanceSensor
     * @param velocitySensor
     * @param distanceCorrectionStrength the correction strength for setting a distance (ex. 0.1 when distance is in feet)
     * @param velocityCorrectionStrength the correction strength for setting a velocity (ex. 1 / max velocity)
     */
    public GSpeedController(SpeedController speedController, DistanceSensor distanceSensor, VelocitySensor velocitySensor, double distanceCorrectionStrength, double velocityCorrectionStrength){
        mSpeedController = speedController;
        mDistanceSensor = distanceSensor;
        mVelocitySensor = velocitySensor;
        mDistanceCorrectionStrength = distanceCorrectionStrength;
        mVelocityCorrectionStrength = velocityCorrectionStrength;
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
        double error = velocity - getVelocity();
        System.out.println(mSpeedController.get() + error * mVelocityCorrectionStrength);
        set(mSpeedController.get() + error * mVelocityCorrectionStrength);
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
        double error = distance - getDistance();
        set(error * mDistanceCorrectionStrength);
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