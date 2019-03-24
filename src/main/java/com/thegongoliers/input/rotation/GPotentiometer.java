package com.thegongoliers.input.rotation;

import com.thegongoliers.annotations.UsedInCompetition;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

@UsedInCompetition(team = "5112", year = "2019")
public class GPotentiometer implements Potentiometer {

    private PIDSourceType pidSource;
    private AnalogInput input;
    private double scale;
    private double zeroPoint;


    /**
     * Creates an analog potentiometer.
     * @param port The analog port that the potentiometer is plugged into.
     * @param scale The full range of motion of the potentiometer (ex. 10 turns = 3600 degrees, so put 3600 here)
     * @param zeroPoint The zero point of the potentiometer. If you expect a position to be 0, but it is reading X degrees, put X here. (ex. actual 1000 to 1100 degrees, expected 0 to 100 degrees. Put 1000 here.)
     */
    public GPotentiometer(int port, double scale, double zeroPoint){
        this.input = new AnalogInput(port);
        this.scale = scale;
        this.zeroPoint = zeroPoint;
    }

    /**
     * Creates an analog potentiometer. Assumes the zero point is 0 degrees.
     * @param port The analog port that the potentiometer is plugged into.
     * @param scale The full range of motion of the potentiometer (ex. 10 turns = 3600 degrees, so put 3600 here)
     */
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