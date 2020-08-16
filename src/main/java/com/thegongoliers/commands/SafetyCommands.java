package com.thegongoliers.commands;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.current.HighCurrentSensor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.DoubleSupplier;

/**
 * This class contains several helper methods to ensure safe operation of the robot (aka. prevents stuff from breaking)
 */
public class SafetyCommands {

    private SafetyCommands(){}

    /**
     * Runs a command when the current is too high
     * @param currentSensor the high current sensor
     * @param command the command to run
     * @return the trigger that is binded to the high current sensor (was set to automatically start command already)
     */
    public static Trigger whenHighCurrent(HighCurrentSensor currentSensor, Command command){
        Trigger trigger = Hardware.makeTrigger(currentSensor::isTriggered);
        trigger.whenActive(command);
        return trigger;
    }

    /**
     * Runs a command while the current is too high
     * @param currentSensor the high current sensor
     * @param command the command to run
     * @return the trigger that is binded to the high current sensor (was set to automatically start command already)
     */
    public static Trigger whileHighCurrent(HighCurrentSensor currentSensor, Command command){
        Trigger trigger = Hardware.makeTrigger(currentSensor::isTriggered);
        trigger.whileActiveContinuous(command);
        return trigger;
    }

    /**
     * Runs a command when the value exceeds the range
     * @param value a function which retrieves a value
     * @param minimum the minimum the value can be (inclusive)
     * @param maximum the maximum the value can be (inclusive)
     * @param command the command to run
     * @return the trigger that is binded to the range (was set to automatically start command already)
     */
    public static Trigger whenOutOfBounds(DoubleSupplier value, double minimum, double maximum, Command command){
        Trigger trigger = Hardware.makeTrigger(() -> value.getAsDouble() < minimum || value.getAsDouble() > maximum);
        trigger.whenActive(command);
        return trigger;
    }

    /**
     * Runs a command while the value exceeds the range
     * @param value a function which retrieves a value
     * @param minimum the minimum the value can be (inclusive)
     * @param maximum the maximum the value can be (inclusive)
     * @param command the command to run
     * @return the trigger that is binded to the range (was set to automatically start command already)
     */
    public static Trigger whileOutOfBounds(DoubleSupplier value, double minimum, double maximum, Command command){
        Trigger trigger = Hardware.makeTrigger(() -> value.getAsDouble() < minimum || value.getAsDouble() > maximum);
        trigger.whileActiveContinuous(command);
        return trigger;
    }

}
