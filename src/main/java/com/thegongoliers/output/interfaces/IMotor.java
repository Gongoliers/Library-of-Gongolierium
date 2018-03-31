package com.thegongoliers.output.interfaces;


public interface IMotor extends Stoppable {

    enum Direction {
        Forward,
        Backward,
        Stopped
    }

    /**
     * Set the voltage of the motor.
     * @param voltage The voltage in volts.
     * @param direction The direction of the motor.
     */
    void setVoltage(double voltage, Direction direction);

    /**
     * Set the voltage of the motor. Negative voltages are assumed to be Backward direction.
     * @param voltage The voltage in volts.
     */
    void setVoltage(double voltage);


    /**
     * Get the voltage of the motor.
     * @return The voltage in volts.
     */
    double getVoltage();

    /**
     * Set the PWM proportion of the motor
     * @param pwm The pwm from 0 to 1.
     * @param direction The direction of the motor.
     */
    void setPWM(double pwm, Direction direction);

    /**
     * Set the PWM proportion of the motor. Negative proportions are assumed to be Backward direction.
     * @param pwm The pwm from -1 to 1.
     */
    void setPWM(double pwm);

    /**
     * Get the PWM proportion of the motor.
     * @return The PWM proportion of the motor from 0 to 1.
     */
    double getPWM();

    /**
     * Get the direction of the motor.
     * @return The direction of the motor.
     */
    Direction getDirection();

    /**
     * Sets if the motor is inverted.
     * @param inverted True if inverted, false otherwise.
     */
    void setInverted(boolean inverted);

    /**
     * Determines if the motor is inverted.
     * @return True if inverted, false otherwise.
     */
    boolean isInverted();

    /**
     * Follow the movement of another motor.
     * @param motor The motor to follow.
     */
    void follow(IMotor motor);

    /**
     * Unfollow the movement of another motor.
     * @param motor The motor to unfollow.
     */
    void unfollow(IMotor motor);

    /**
     * Add a follower.
     * @param motor The motor which will follow this motor.
     */
    void addFollower(IMotor motor);

    /**
     * Remove a follower.
     * @param motor The motor which will unfollow this motor.
     */
    void removeFollower(IMotor motor);

}
