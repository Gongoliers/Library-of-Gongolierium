package com.thegongoliers.input.odometry;

import com.thegongoliers.utils.Resettable;

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

    /**
     * Convert an encoder to an encoder sensor
     * @param encoder the WPILib encoder
     * @return the encoder sensor
     */
    public static EncoderSensor toEncoderSensor(Encoder encoder){
        return new WPIEncoderSensor(encoder);
    }

    public static EncoderSensor toEncoderSensor(DistanceSensor distance, VelocitySensor velocity, Resettable reset){
        return new BaseEncoderSensor(distance, velocity, reset);
    }

    public static EncoderSensor toEncoderSensor(DistanceSensor distance, VelocitySensor velocity){
        return new BaseEncoderSensor(distance, velocity);
    }

}
