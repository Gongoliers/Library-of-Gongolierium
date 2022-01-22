package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public final class EncoderUtils {

    /**
     * Convert an encoder to a distance sensor
     * @param encoder the encoder
     * @return the distance sensor
     */
    public static DistanceSensor toDistanceSensor(Encoder encoder){
        return encoder::getDistance;
    }

    /**
     * Convert an encoder to a velocity sensor
     * @param encoder the encoder
     * @return the velocity sensor
     */
    public static VelocitySensor toVelocitySensor(Encoder encoder) {
        return encoder::getRate;
    }

}
