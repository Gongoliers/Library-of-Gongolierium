package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

import java.util.Objects;

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

        forward = GMath.clamp(forward, -1, 1);
        turn = GMath.clamp(turn, -1, 1);

        if (GMath.approximately(forward, 0)) forward = 0;
        if (GMath.approximately(turn, 0)) turn = 0;
    
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
    
        return new DriveSpeed(left, right);
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


    public DriveSpeed plus(DriveSpeed other){
      double l = left + other.left;
      double r = right + other.right;
      return new DriveSpeed(GMath.clamp(l, -1, 1), GMath.clamp(r, -1, 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveSpeed that = (DriveSpeed) o;
        return Double.compare(that.left, left) == 0 && Double.compare(that.right, right) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}