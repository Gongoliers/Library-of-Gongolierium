package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.switches.ResettableSwitch;

public class MockResettableSwitch implements ResettableSwitch {
    private boolean triggered = false;

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    @Override
    public void reset() {
        triggered = false;
    }
}
