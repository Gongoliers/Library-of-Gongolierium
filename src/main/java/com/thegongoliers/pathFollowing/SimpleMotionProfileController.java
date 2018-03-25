package com.thegongoliers.pathFollowing;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.math.MathExt;

public class SimpleMotionProfileController {

    private double kp;
    private double ki;
    private double kd;
    private double kv;
    private double ka;

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
     * @param kv The velocity feed forward constant. Typically 1 / max velocity.
     * @param ka The acceleration feed forward constant.
     */
    public SimpleMotionProfileController(double kp, double ki, double kd, double kv, double ka) {
        this(kp, ki, kd, kv, ka, new RobotClock());
    }

    /**
     * Generates a simple motion profile controller
     * @param kp The proportional feedback constant.
     * @param ki The integral feedback constant.
     * @param kd The derivative feedback constant.
     * @param kv The velocity feed forward constant. Typically 1 / max velocity.
     * @param ka The acceleration feed forward constant.
     * @param clock The clock to use.
     */
    public SimpleMotionProfileController(double kp, double ki, double kd, double kv, double ka, Clock clock) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kv = kv;
        this.ka = ka;
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
        double vTerm = kv * targetVelocity;
        double aTerm = ka * targetAcceleration;

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
     * Calculate the output value of the controller.
     * @param currentPosition The current position of the system.
     * @param targetPosition The target position of the system.
     * @return The output of the controller.
     */
    public double calculate(double currentPosition, double targetPosition){
        return calculate(currentPosition, targetPosition, 0, 0);
    }

}
