package com.thegongoliers.input.rotation;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class GPotentiometer implements Potentiometer {

    private PIDSourceType pidSource;
    private AnalogInput input;
    private double scale;
    private double zeroPoint;


    public GPotentiometer(int port, double scale, double zeroPoint){
        this.input = new AnalogInput(port);
        this.scale = scale;
        this.zeroPoint = zeroPoint;
    }

    public GPotentiometer(int port, double scale){
        this(port, scale, 0);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    @Override
    public double pidGet() {
        return get();
    }

    @Override
    public double get() {
        if (input == null){
            return zeroPoint;
        }
        return (input.getVoltage() / RobotController.getVoltage5V()) * scale - zeroPoint;
	}

}