package com.thegongoliers.output.control;

import com.kylecorry.pid.PID;
import com.kylecorry.pid.Range;

public class PIDController implements MotionController {

    private final PID pid;
    private double mSetpoint = 0.0;
    private double mMaxOutput = 1.0;

    public PIDController(double p, double i, double d) {
        pid = new PID(p, i, d);
    }

    public void setPositionTolerance(double tolerance) {
        pid.setPositionTolerance(tolerance);
    }

    public void setVelocityTolerance(double tolerance) {
        pid.setVelocityTolerance(tolerance);
    }

    public void setIntegratorRange(double min, double max) {
        pid.setIntegratorRange(new Range(min, max));
    }

    public void setMaxOutput(double max){
        mMaxOutput = max;
    }

    @Override
    public void setSetpoint(double setpoint) {
        mSetpoint = setpoint;
    }

    @Override
    public double calculate(double actual, double deltaTime) {
        var calculated = pid.calculate(actual, mSetpoint, deltaTime);
        return Math.min(mMaxOutput, Math.abs(calculated)) * Math.signum(calculated);
    }

    @Override
    public boolean atSetpoint() {
        return pid.atSetpoint();
    }

    @Override
    public MotionController copy() {
        var pidCopy = new PIDController(pid.getP(), pid.getI(), pid.getD());
        pidCopy.setPositionTolerance(pid.getPositionTolerance());
        pidCopy.setVelocityTolerance(pid.getVelocityTolerance());
        pidCopy.setIntegratorRange(pid.getIntegratorRange().getMin(), pid.getIntegratorRange().getMax());
        return pidCopy;
    }

    @Override
    public void reset() {
        pid.reset();
    }
}
