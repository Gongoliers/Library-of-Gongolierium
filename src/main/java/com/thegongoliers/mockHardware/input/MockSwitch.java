package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.switches.Switch;

public class MockSwitch implements Switch {
    private boolean triggered = false;

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }
}
