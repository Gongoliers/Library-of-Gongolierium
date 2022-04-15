package com.thegongoliers.output.gears;

public interface Ratchet {
    RatchetState get();

    boolean isSet();

    void set(RatchetState state);

    default boolean isAllowed(double speed) {
        if (speed == 0.0) {
            return true;
        }

        if (!isSet()) {
            return false;
        }

        var state = get();

        switch (state) {
            case Neutral:
                return true;
            case LockReverse:
                return speed > 0;
            case LockForward:
                return speed < 0;
        }

        return false;
    }
}
