package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.ISolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class FRCSolenoid implements ISolenoid {

    private Solenoid solenoid;

    public FRCSolenoid(int channel){
        solenoid = new Solenoid(channel);
    }

    public FRCSolenoid(int moduleNumber, int channel){
        solenoid = new Solenoid(moduleNumber, channel);
    }

    @Override
    public void set(boolean on) {
        solenoid.set(on);
    }

    @Override
    public boolean get() {
        return solenoid.get();
    }
}
