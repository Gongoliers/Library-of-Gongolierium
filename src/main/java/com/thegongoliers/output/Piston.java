package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.IPiston;
import com.thegongoliers.output.interfaces.ISolenoid;

public class Piston implements IPiston {

    private ISolenoid solenoid;
    private boolean inverted = false;

    public Piston(ISolenoid solenoid) {
        this.solenoid = solenoid;
    }

    @Override
    public void extend() {
        solenoid.set(!inverted);
    }

    @Override
    public void retract() {
        solenoid.set(inverted);
    }

    @Override
    public boolean isExtended() {
        return solenoid.get() != inverted;
    }

    @Override
    public boolean isRetracted() {
        return solenoid.get() == inverted;
    }

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }
}
