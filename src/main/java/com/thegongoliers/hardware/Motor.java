package com.thegongoliers.hardware;

import com.thegongoliers.math.ExponentialMovingAverage;
import com.thegongoliers.output.PID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Created by Kyle on 7/9/2017.
 */
public class Motor {

    private Encoder encoder;
    private SpeedController speedController;
    private PID pidController;
    private ExponentialMovingAverage ema;

    /**
     * Creates a motor which is monitored by an encoder.
     *
     * @param speedController The speed controller for the motor.
     * @param encoder         The encoder on the motor shaft. The encoder distance per pulse should be set to 1 / pulses per revolution.
     * @param pidController   The PID controller for closed loop feedback.
     */
    public Motor(SpeedController speedController, Encoder encoder, PID pidController) {
        this.encoder = encoder;
        this.speedController = speedController;
        this.pidController = pidController;
        ema = new ExponentialMovingAverage(10);
    }

    /**
     * Creates a motor which is monitored by an encoder.
     *
     * @param speedController The speed controller for the motor.
     * @param encoder         The encoder on the motor shaft. The encoder distance per pulse should be set to 1 / pulses per revolution.
     */
    public Motor(SpeedController speedController, Encoder encoder) {
        this(speedController, encoder, new PID(0.25, 0, 0.01, 0.01));
    }

    /**
     * Set the throttle of the motor.
     *
     * @param throttle The throttle of the motor from -100 to 100 percent inclusive, where -100 is full reverse.
     */
    public void setThrottle(double throttle) {
        speedController.set(-throttle / 100.0);
    }

    /**
     * Get the throttle of the motor.
     *
     * @return The throttle of the motor from -100 to 100 percent inclusive, where -100 is full reverse.
     */
    public double getThrottle() {
        return speedController.get() * -100.0;
    }

    /**
     * Set the RPM of the motor.
     *
     * @param rpm The rpm of the motor.
     */
    public void setRPM(double rpm) {
        speedController.set(pidController.getOutput(getRPM(), rpm));
    }

    /**
     * Get the RPM of the motor.
     *
     * @return The RPM of the motor.
     */
    public double getRPM() {
        return ema.calculate(encoder.getRate() * 60.0);
    }


}
