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
     * Set the bus percentage of the motor
     * @param percentage The bus percentage from 0 to 100.
     * @param direction The direction of the motor.
     */
    void setBusPercentage(double percentage, Direction direction);

    /**
     * Set the bus percentage of the motor. Negative percentages are assumed to be Backward direction.
     * @param percentage The bus percentage from -100 to 100.
     */
    void setBusPercentage(double percentage);

    /**
     * Get the bus percentage of the motor.
     * @return The bus percentage of the motor from 0 to 100.
     */
    double getBusPercentage();

    /**
     * Get the current of the motor.
     * @return The current in amps.
     */
    double getCurrent();

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

}
