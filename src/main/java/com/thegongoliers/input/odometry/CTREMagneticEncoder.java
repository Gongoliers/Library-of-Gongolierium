package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public class CTREMagneticEncoder extends Encoder {

    private static final int PULSES_PER_REVOLUTION = 4096;

    public CTREMagneticEncoder(int channelA, int channelB, boolean reverseDirection) {
        super(channelA, channelB, reverseDirection, EncodingType.k4X);
        setDistancePerRevolution(360);
    }

    public void setDistancePerRevolution(double distance){
        setDistancePerPulse(distance / PULSES_PER_REVOLUTION);
    }
}