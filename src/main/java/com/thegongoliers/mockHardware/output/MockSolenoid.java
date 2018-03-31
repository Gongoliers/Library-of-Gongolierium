package com.thegongoliers.mockHardware.output;

import com.thegongoliers.output.interfaces.ISolenoid;

public class MockSolenoid implements ISolenoid {

    private boolean on = false;

    @Override
    public void set(boolean on) {
        this.on = on;
    }

    @Override
    public boolean get() {
        return on;
    }
}
