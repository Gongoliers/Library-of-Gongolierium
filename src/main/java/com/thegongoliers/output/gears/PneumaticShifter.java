package com.thegongoliers.output.gears;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * A pneumatic shifter which has a single low and high gear
 */
public class PneumaticShifter implements GearShifter {

    private Solenoid shifter;
    private boolean inverted;

    /**
     * Default constructor
     * @param shifter the shifter
     * @param inverted true if the solenoid is inverted
     */
    public PneumaticShifter(Solenoid shifter, boolean inverted){
        this.shifter = shifter;
        this.inverted = inverted;
    }

    /**
     * Constructor
     * @param shifter the shifter
     */
    public PneumaticShifter(Solenoid shifter){
        this(shifter, false);
    }

    @Override
    public void upshift() {
        shifter.set(!inverted);
    }

    @Override
    public void downshift() {
        shifter.set(inverted);
    }

    @Override
    public int getGear() {
        boolean solenoidValue = shifter.get();
        return (solenoidValue == inverted) ? 1 : 2;
    }

    @Override
    public int getTotalGears() {
        return 2;
    }

}