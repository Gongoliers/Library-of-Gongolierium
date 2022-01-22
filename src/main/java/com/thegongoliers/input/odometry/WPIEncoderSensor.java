package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public class WPIEncoderSensor implements EncoderSensor {

    private Encoder mEncoder;

    public WPIEncoderSensor(Encoder encoder){
        mEncoder = encoder;
    }

    @Override
    public double getDistance() {
        return mEncoder.getDistance();
    }

    @Override
    public double getVelocity() {
        return mEncoder.getRate();
    }

    @Override
    public void reset() {
        mEncoder.reset();        
    }
}
