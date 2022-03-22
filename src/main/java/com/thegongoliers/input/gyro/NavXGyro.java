package com.thegongoliers.input.gyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Allows a Navx to be referenced as a Gyro.
 */
public class NavXGyro implements Gyro {

    private AHRS navx;

    public NavXGyro(AHRS navx) {
        this.navx = navx;
    }

    @Override
    public void close() throws Exception {
        // intentionally empty since you cannot close Navx
    }

    @Override
    public void calibrate() {
        // intentionally empty since Navx auto-calibrates
    }

    @Override
    public void reset() {
        navx.zeroYaw();
    }

    @Override
    public double getAngle() {
        return navx.getAngle();
    }

    @Override
    public double getRate() {
        return navx.getRate();
    }

}
