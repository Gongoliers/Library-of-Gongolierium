package com.thegongoliers.pathFollowing.controllers;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.math.MathExt;

public class MotionProfileController implements MotionController {

    private final double kp;
    private final double ki;
    private final double kd;
    private final double kffv;
    private final double kffa;
    private final double tolerance;

    private double prevError;

    private double iState;
    private double maxOutput = 1;
    private double minOutput = -1;
    private double maxI = maxOutput;
    private double minI = minOutput;

    private Clock clock;

    private double lastTime = -1;

    /**
     * Generates a simple motion profile controller
     * @param kp The proportional feedback constant.
     * @param ki The integral feedback constant.
     * @param kd The derivative feedback constant.
     * @param kffv The velocity feed forward constant. Typically 1 / max velocity.
     * @param kffa The acceleration feed forward constant.
     * @param tolerance The absolute tolerance.
     */
    public MotionProfileController(double kp, double ki, double kd, double kffv, double kffa, double tolerance) {
        this(kp, ki, kd, kffv, kffa, tolerance, new RobotClock());
    }

    /**
     * Generates a simple motion profile controller
     * @param kp The proportional feedback constant.
     * @param ki The integral feedback constant.
     * @param kd The derivative feedback constant.
     * @param kffv The velocity feed forward constant. Typically 1 / max velocity.
     * @param kffa The acceleration feed forward constant.
     * @param tolerance The absolute tolerance.
     * @param clock The clock to use.
     */
    public MotionProfileController(double kp, double ki, double kd, double kffv, double kffa, double tolerance, Clock clock) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kffv = kffv;
        this.kffa = kffa;
        this.tolerance = tolerance;
        this.clock = clock;
    }

    /**
     * Gets the maximum output of the controller.
     * @return The maximum output.
     */
    public double getMaxOutput() {
        return maxOutput;
    }

    /**
     * Sets the maximum output of the controller.
     * @param maxOutput The maximum output of the controller.
     */
    public void setMaxOutput(double maxOutput) {
        this.maxOutput = maxOutput;
    }

    /**
     * Gets the minimum output of the controller.
     * @return The minimum output.
     */
    public double getMinOutput() {
        return minOutput;
    }

    /**
     * Sets the minimum output of the controller.
     * @param minOutput The minimum output of the controller.
     */
    public void setMinOutput(double minOutput) {
        this.minOutput = minOutput;
    }

    /**
     * Get the maximum allowed integral error.
     * @return The maximum integral error.
     */
    public double getMaxIntegralError() {
        return maxI;
    }

    /**
     * Sets the maximum allowed integral error.
     * @param maxIntegralError The maximum integral error.
     */
    public void setMaxIntegralError(double maxIntegralError) {
        this.maxI = maxIntegralError;
    }

    /**
     * Gets the minimum allowed integral error.
     * @return The minimum allowed integral error.
     */
    public double getMinIntegralError() {
        return minI;
    }

    /**
     * Sets the minimum allowed integral error.
     * @param minIntegralError The minimum allowed integral error.
     */
    public void setMinIntegralError(double minIntegralError) {
        this.minI = minIntegralError;
    }

    /**
     * Gets the clock being used.
     * @return The clock.
     */
    public Clock getClock() {
        return clock;
    }

    /**
     * Sets the clock to be used.
     * @param clock The clock.
     */
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Resets the controller's state. Use this before using the controller another time.
     */
    public void reset(){
        iState = 0;
        prevError = 0;
        lastTime = -1;
    }

    /**
     * @see MotionController#isOnTarget(double, double)
     */
    public boolean isOnTarget(double currentPosition, double targetPosition){
        double error = targetPosition - currentPosition;
        return Math.abs(error) <= tolerance;
    }

    /**
     * Calculate the output value of the controller.
     * @param currentPosition The current position of the system.
     * @param targetPosition The target position of the system.
     * @param targetVelocity The target velocity of the system.
     * @param targetAcceleration The target acceleration of the system.
     * @return The output of the controller.
     */
    public double calculate(double currentPosition, double targetPosition, double targetVelocity, double targetAcceleration){
        double error = targetPosition - currentPosition;

        double pTerm = kp * error;
        double dTerm = 0;
        double vTerm = kffv * targetVelocity;
        double aTerm = kffa * targetAcceleration;

        iState += error;
        iState = MathExt.toRange(iState, minI, maxI);
        double iTerm = ki * iState;

        double currentTime = clock.getTime();

        if(lastTime != -1){
            dTerm = kd * ((error - prevError) / (currentTime - lastTime) - targetVelocity);
        }



        double value = pTerm + iTerm + dTerm + vTerm + aTerm;

        lastTime = currentTime;
        prevError = error;

        return MathExt.toRange(value, minOutput, maxOutput);
    }

    /**
     * Calculate the output value of the controller.
     * @param currentPosition The current position of the system.
     * @param targetPosition The target position of the system.
     * @param targetVelocity The target velocity of the system.
     * @return The output of the controller.
     */
    public double calculate(double currentPosition, double targetPosition, double targetVelocity){
        return calculate(currentPosition, targetPosition, targetVelocity, 0);
    }

    /**
     * @see MotionController#calculate(double, double)
     */
    public double calculate(double currentPosition, double targetPosition){
        return calculate(currentPosition, targetPosition, 0, 0);
    }

}
