package com.thegongoliers.input;

import com.thegongoliers.input.current.CurrentSensor;
import com.thegongoliers.input.current.PDPCurrentSensor;
import com.thegongoliers.input.voltage.BatteryVoltageSensor;
import com.thegongoliers.input.voltage.VoltageSensor;
import com.thegongoliers.math.MathExt;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDP {

    private static PDP instance;
    private final PowerDistributionPanel pdp;
    private static final double BATTERY_FULL_VOLTAGE = 13;
    private static final double BATTERY_EMPTY_VOLTAGE = 11.3;

    private PDP(){
        pdp = new PowerDistributionPanel();
    }

    public static PDP getInstance(){
        if(instance == null)
            instance = new PDP();
        return instance;
    }

    public VoltageSensor getBatteryVoltageSensor(){
        return new BatteryVoltageSensor();
    }

    public double getBatteryVoltage(){
        return pdp.getVoltage();
    }

    public double getBatteryPercent(){
        double voltageSlope = (BATTERY_FULL_VOLTAGE - BATTERY_EMPTY_VOLTAGE) / 100.0;
        double rawPercent = (getBatteryVoltage() - BATTERY_EMPTY_VOLTAGE) / voltageSlope;

        return MathExt.toRange(rawPercent, 0, 100);
    }

    public CurrentSensor getCurrentSensor(int port){
        return new PDPCurrentSensor(pdp, port);
    }

    public double getCurrent(int port){
        return getCurrentSensor(port).getCurrent();
    }

}
