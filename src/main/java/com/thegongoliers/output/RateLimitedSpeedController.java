package com.thegongoliers.output;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.math.filter.Filter;
import com.thegongoliers.math.filter.RateLimiter;
import edu.wpi.first.wpilibj.SpeedController;

public class RateLimitedSpeedController implements SpeedController {

    private Filter rateLimiter;
    private SpeedController controller;
    private double lastSpeed;
    private double maxRate;

    public RateLimitedSpeedController(SpeedController controller, double maxRate){
        if (controller == null){
            throw new GongolieriumException("Speed controller can't be null");
        }
        if (maxRate <= 0){
            throw new GongolieriumException("Max rate must be positive");
        }
        this.controller = controller;
        lastSpeed = controller.get();
        this.maxRate = maxRate;
        rateLimiter = new RateLimiter(maxRate, lastSpeed);
    }

    @Override
    public void set(double speed) {
        if (controller.get() != lastSpeed){
            rateLimiter = new RateLimiter(maxRate, controller.get());
        }
        controller.set(rateLimiter.filter(speed));
        lastSpeed = controller.get();
    }

    @Override
    public double get() {
        return controller.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        controller.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return controller.getInverted();
    }

    @Override
    public void disable() {
        controller.disable();
    }

    @Override
    public void stopMotor() {
        controller.stopMotor();
    }

    @Override
    public void pidWrite(double output) {
        controller.pidWrite(output);
    }
}
