package com.thegongoliers.input.velocity;

import java.util.function.DoubleSupplier;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

public class VelocitySensor {

    private Clock clock;
    private DoubleSupplier source;

    private double lastTime;
    private double lastValue;


    public VelocitySensor(DoubleSupplier source, Clock clock){
        this.source = source;
        this.clock = clock;
        this.lastTime = clock.getTime();
        this.lastValue = source.getAsDouble();
    }

    public VelocitySensor(DoubleSupplier source){
        this(source, new RobotClock());
    }

    public double getVelocity(){
        double time = clock.getTime();
        double dt = time - lastTime;

        double value = source.getAsDouble();
        double dv = value - lastValue;

        if(dt == 0){
            dt = 0.00001;
        }

        lastTime = time;
        lastValue = value;

        return dv / dt;
    }


}