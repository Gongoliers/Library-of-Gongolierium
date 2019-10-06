package com.thegongoliers.input.power;

import com.thegongoliers.input.current.CurrentSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

/**
 * A software sensor for measuring electric discharge
 */
public class CoulombCounter {

    private CurrentSensor sensor;
    private Clock clock;

    private double lastReadingTime;
    private double lastReading;
    private double accumulatedReading;

    private static final int SECONDS_TO_HOURS = 3600;

    /**
     * Default constructor
     * @param sensor the current sensor which measures battery current
     */
    public CoulombCounter(CurrentSensor sensor){
        this(sensor, new RobotClock());
    }

    /**
     * Test constructor
     * @param sensor the current sensor which measures battery current
     * @param clock the clock to use
     */
    public CoulombCounter(CurrentSensor sensor, Clock clock){
        this.sensor = sensor;
        this.clock = clock;
        lastReadingTime = clock.getTime();
        lastReading = 0;
        accumulatedReading = 0;
    }

    /**
     * Update the current and total amp-hours. Should be called regularly.
     */
    public void update(){
        double time = clock.getTime();
        double current = sensor.getCurrent();

        double dt = time - lastReadingTime;
        lastReading = dt * current;
        accumulatedReading += lastReading;
        lastReadingTime = time;
    }

    /**
     * Get the current discharge in Amp-hours (Ah)
     * @return the current electrical discharge
     */
    public double getCurrentDischarge(){
        return lastReading * SECONDS_TO_HOURS;
    }

    /**
     * Get the total discharge in Amp-hours (Ah)
     * @return the total electrical discharge
     */
    public double getTotalDischarge(){
        return accumulatedReading * SECONDS_TO_HOURS;
    }

}