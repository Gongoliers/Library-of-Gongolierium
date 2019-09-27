package com.thegongoliers.output.drivemodules;

/**
 * A drive value which is used to control the speed of the drivetrain
 */
public class DriveValue {

    private double forwardSpeed, turnSpeed;

    /**
     * Default constructor
     * @param forward the forward speed
     * @param turn the turn speed
     */
    public DriveValue(double forward, double turn){
        this.forwardSpeed = forward;
        this.turnSpeed = turn;
    }

    /**
     * @return the forward speed
     */
    public double getForwardSpeed(){
        return forwardSpeed;
    }

    /**
     * @return the turning speed
     */
    public double getTurnSpeed(){
        return turnSpeed;
    }

    // TODO: Add tank mode support

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DriveValue)) return false;
        DriveValue other = (DriveValue) obj;
        return other.turnSpeed == turnSpeed && other.forwardSpeed == forwardSpeed;
    }

    @Override
    public String toString() {
        return String.format("Drive value: %0.2f, %0.2f", forwardSpeed, turnSpeed);
    }

}