package com.thegongoliers.output.control;

import com.thegongoliers.utils.Resettable;

public interface MotionController extends Resettable {

    void setSetpoint(double setpoint);

    double calculate(double actual, double deltaTime);

    boolean atSetpoint();

}
