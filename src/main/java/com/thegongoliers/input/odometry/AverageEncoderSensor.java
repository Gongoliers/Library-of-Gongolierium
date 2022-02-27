package com.thegongoliers.input.odometry;

import java.util.Arrays;
import java.util.Collection;

public class AverageEncoderSensor implements EncoderSensor {

    private Collection<EncoderSensor> encoders;

    public AverageEncoderSensor(EncoderSensor... encoders){
        this.encoders = Arrays.asList(encoders);
    }

    public AverageEncoderSensor(Collection<EncoderSensor> encoders){
        this.encoders = encoders;
    }

    @Override
    public double getDistance() {
        if (encoders.isEmpty()){
            return 0.0;
        }
        return encoders.stream().mapToDouble(EncoderSensor::getDistance).average().getAsDouble();
    }

    @Override
    public double getVelocity() {
        if (encoders.isEmpty()){
            return 0.0;
        }
        return encoders.stream().mapToDouble(EncoderSensor::getVelocity).average().getAsDouble();
    }

    @Override
    public void reset() {
        encoders.stream().forEach(EncoderSensor::reset);
    }
    
}
