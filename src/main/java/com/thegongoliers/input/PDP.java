package com.thegongoliers.input;

import com.thegongoliers.input.current.CurrentSensor;
import com.thegongoliers.input.current.PDPCurrentSensor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDP {

    private static PDP instance;
    private final PowerDistributionPanel pdp;

    private PDP(){
        pdp = new PowerDistributionPanel();
    }

    public static PDP getInstance(){
        if(instance == null)
            instance = new PDP();
        return instance;
    }

    public CurrentSensor getCurrentSensor(int port){
        return new PDPCurrentSensor(pdp, port);
    }

    public double getCurrent(int port){
        return getCurrentSensor(port).getCurrent();
    }

    public CurrentSensor getBatteryCurrentSensor(){
        return pdp::getTotalCurrent;
    }

}
