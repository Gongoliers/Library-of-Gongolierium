package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A class representing the speed of a differential drivetrain
 */
public class DriveSpeed {

    public static final DriveSpeed STOP = new DriveSpeed(0, 0);

    private double left, right;

    /**
     * Default constructor
     * @param left the left speed
     * @param right the right speed
     */
    public DriveSpeed(double left, double right){
        this.left = GMath.clamp(left, -1, 1);
        this.right = GMath.clamp(right, -1, 1);
    }

    /**
     * Convert arcade inputs into tank
     * @param forward the forward speed
     * @param turn the turn speed
     * @return the tank speed
     */
    public static DriveSpeed fromArcade(double forward, double turn){
        double left;
        double right;
    
        double maxInput = Math.copySign(Math.max(Math.abs(forward), Math.abs(turn)), forward);
    
        if (forward >= 0.0) {
          // First quadrant, else second quadrant
          if (turn >= 0.0) {
            left = maxInput;
            right = forward - turn;
          } else {
            left = forward + turn;
            right = maxInput;
          }
        } else {
          // Third quadrant, else fourth quadrant
          if (turn >= 0.0) {
            left = forward + turn;
            right = maxInput;
          } else {
            left = maxInput;
            right = forward - turn;
          }
        }
    
        return new DriveSpeed(GMath.clamp(left, -1, 1), GMath.clamp(right, -1, 1));
    }

    /**
     * The left speed of the drivetrain
     * @return the left speed
     */
    public double getLeftSpeed(){
        return left;
    }

    /**
     * The right speed of the drivetrain
     * @return the right speed
     */
    public double getRightSpeed(){
        return right;
    }

}