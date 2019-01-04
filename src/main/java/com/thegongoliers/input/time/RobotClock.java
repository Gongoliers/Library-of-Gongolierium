package com.thegongoliers.input.time;

import edu.wpi.first.wpilibj.Timer;

public class RobotClock implements Clock{
    @Override
    public double getTime() {
        return Timer.getFPGATimestamp();
    }
}
