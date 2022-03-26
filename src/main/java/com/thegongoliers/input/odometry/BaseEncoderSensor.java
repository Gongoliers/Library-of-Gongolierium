package com.thegongoliers.input.odometry;

import com.thegongoliers.utils.Resettable;

public class BaseEncoderSensor implements EncoderSensor {

    private double mLastDistance = 0.0;
    private DistanceSensor mDistanceSensor;
    private VelocitySensor mVelocitySensor;
    private Resettable mResettable;

    public BaseEncoderSensor(DistanceSensor distance, VelocitySensor velocity){
        this(distance, velocity, null);
    }

    public BaseEncoderSensor(DistanceSensor distance, VelocitySensor velocity, Resettable resettable){
        mDistanceSensor = distance;
        mVelocitySensor = velocity;
        mResettable = resettable;
    }

    @Override
    public double getDistance() {
        return mDistanceSensor.getDistance() - mLastDistance;
    }

    @Override
    public double getVelocity() {
        return mVelocitySensor.getVelocity();
    }

    @Override
    public void reset() {
        if (mResettable != null){
            mResettable.reset();
            return;
        }

        mLastDistance = mDistanceSensor.getDistance();
    }
    
}
