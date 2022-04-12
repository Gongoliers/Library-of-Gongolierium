package com.thegongoliers.output.drivetrain;

import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.input.voltage.BatteryVoltageSensor;
import com.thegongoliers.input.voltage.VoltageSensor;

/**
 * Ensures that the drivetrain runs at a consistent voltage, compensating for battery voltage fluctuations
 */
@UsedInCompetition(team = "5112", year = "2022")
public class VoltageControlModule implements DriveModule {

    private double mMaxVoltage;
    private VoltageSensor mBatteryVoltage;

    /**
     * @param maxVoltage The maximum voltage of the drivetrain motors when the input speed is 1 (in volts)
     * @param batteryVoltage The battery voltage sensor
     */
    public VoltageControlModule(double maxVoltage, VoltageSensor batteryVoltage){
        setMaxVoltage(maxVoltage);
        mBatteryVoltage = batteryVoltage;
    }

    /**
     * @param maxVoltage The maximum voltage of the drivetrain motors when the input speed is 1 (in volts)
     */
    public VoltageControlModule(double maxVoltage){
        this(maxVoltage, new BatteryVoltageSensor());
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        var leftVoltage = calculateDesiredVoltage(desiredSpeed.getLeftSpeed());
        var rightVoltage = calculateDesiredVoltage(desiredSpeed.getRightSpeed());

        return voltagesToSpeed(leftVoltage, rightVoltage);
    }

    public void setMaxVoltage(double maxVoltage){
        if (maxVoltage <= 0) throw new IllegalArgumentException("Max voltage must be greater than 0.");
        mMaxVoltage = maxVoltage;
    }

    private double calculateDesiredVoltage(double desiredSpeed){
        return desiredSpeed * mMaxVoltage;
    }

    private DriveSpeed voltagesToSpeed(double leftVoltage, double rightVoltage){
        return new DriveSpeed(voltageToSpeed(leftVoltage), voltageToSpeed(rightVoltage));
    }

    private double voltageToSpeed(double voltage){
        return voltage / mBatteryVoltage.getVoltage();
    }

}