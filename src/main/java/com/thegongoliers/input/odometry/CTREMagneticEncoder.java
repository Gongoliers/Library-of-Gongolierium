package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public class CTREMagneticEncoder extends Encoder {

    public static final int PULSES_PER_REVOLUTION = 4096;

    public CTREMagneticEncoder(int channelA, int channelB, boolean reverseDirection) {
        super(channelA, channelB, reverseDirection, EncodingType.k4X);
        EncoderUtils.setPulsesPerRevolution(this, PULSES_PER_REVOLUTION);
    }

}
