package com.thegongoliers.input.odometry;

import com.thegongoliers.utils.Resettable;
public interface EncoderSensor extends DistanceSensor, VelocitySensor, Resettable {

    public default EncoderSensor scaledBy(double scale){
        return new BaseEncoderSensor(() -> getDistance() * scale, () -> getVelocity() * scale);
    }

    public default EncoderSensor inverted(){
        return new BaseEncoderSensor(() -> -getDistance(), () -> -getVelocity());
    }

}
