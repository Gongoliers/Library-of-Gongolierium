package com.thegongoliers.mockHardware.output;

import com.thegongoliers.output.gears.GearShifter;

/**
 * A mock gear shifter
 */
public class MockGearShifter implements GearShifter {

    private int currentGear;
    private int numGears;

    public MockGearShifter(int numGears){
        this.numGears = numGears;
        this.currentGear = 0;
    }

    public void setGear(int gear){
        this.currentGear = gear - 1;
    }

    @Override
    public void upshift() {
        currentGear++;
        if (currentGear >= numGears) currentGear = numGears - 1;
    }

    @Override
    public void downshift() {
        currentGear--;
        if (currentGear < 0) currentGear = 0;
    }

    @Override
    public int getGear() {
        return currentGear + 1;
    }

    @Override
    public int getTotalGears() {
        return numGears;
    }

}