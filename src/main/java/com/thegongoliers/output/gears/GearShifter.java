package com.thegongoliers.output.gears;

/**
 * A gear shifter
 */
public interface GearShifter {
    /**
     * Shift to a higher gear
     */
    void upshift();

    /**
     * Shift to a lower gear
     */
    void downshift();

    /**
     * @return the current gear
     */
    int getGear();

    /**
     * @return the total number of gears
     */
    int getTotalGears();
}