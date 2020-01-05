package com.thegongoliers.output;

import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.output.interfaces.Piston;

import edu.wpi.first.wpilibj.Solenoid;

@UsedInCompetition(team = "5112", year = "2019")
public class GPiston implements Piston {

    private final Solenoid solenoid;
    private boolean inverted = false;

    public GPiston(Solenoid solenoid) {
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
