package com.thegongoliers.output.subsystems;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Drivetrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A wrapper class for a drivetrain which stabilizes it (maintains heading when driving straight). 
 * This is useful to keep the robot driving straight when driving over obstacles or being hit by another robot.
 */
public class StabilizedDrivetrain implements Drivetrain {

    private Drivetrain drivetrain;
    private Gyro gyro;
    private double lastHeading;
    private double kp;
    private double lastStopTime;
    private final double settlingTime;
    private Clock clock;

    /**
     * Constructor
     * @param drivetrain the drivetrain
     * @param gyro the gyroscope
     * @param correctionFactor the correction factor to remain stabilized (multiplied by the heading error in degrees). 0.01 has worked well in the past for the Gongoliers
     */
    public StabilizedDrivetrain(Drivetrain drivetrain, Gyro gyro, double correctionFactor){
        this(drivetrain, gyro, correctionFactor, 0);
    }

    /**
     * Constructor
     * @param drivetrain the drivetrain
     * @param gyro the gyroscope
     * @param correctionFactor the correction factor to remain stabilized (multiplied by the heading error in degrees). 0.01 has worked well in the past for the Gongoliers
     * @param settlingTime the time to allow the drivetrain to settle to a stop before correcting the heading
     */
    public StabilizedDrivetrain(Drivetrain drivetrain, Gyro gyro, double correctionFactor, double settlingTime){
        this(drivetrain, gyro, correctionFactor, settlingTime, new RobotClock());
    }

    /**
     * Constructor
     * @param drivetrain the drivetrain
     * @param gyro the gyroscope
     * @param correctionFactor the correction factor to remain stabilized (multiplied by the heading error in degrees). 0.01 has worked well in the past for the Gongoliers
     * @param settlingTime the time to allow the drivetrain to settle to a stop before correcting the heading
     * @param clock the clock to use to monitor the settling time
     */
    public StabilizedDrivetrain(Drivetrain drivetrain, Gyro gyro, double correctionFactor, double settlingTime, Clock clock){
        this.drivetrain = drivetrain;
        this.gyro = gyro;
        lastHeading = gyro.getAngle();
        kp = correctionFactor;
        this.settlingTime = settlingTime;
        this.clock = clock;
        lastStopTime = 0;
    }


    @Override
    public void stop() {
        drivetrain.stop();
    }

    @Override
    public void arcade(double speed, double turn) {
        if (turn != 0){
            drivetrain.arcade(speed, turn);
            lastHeading = gyro.getAngle();
            lastStopTime = clock.getTime();
        } else {
            if (clock.getTime() - lastStopTime < settlingTime){
                lastHeading = gyro.getAngle();
            }
            drivetrain.arcade(speed, -kp * (gyro.getAngle() - lastHeading));
        }
    }

    @Override
    public void tank(double leftSpeed, double rightSpeed) {
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    
}