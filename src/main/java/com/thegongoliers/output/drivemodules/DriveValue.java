package com.thegongoliers.output.drivemodules;

public class DriveValue {

    private double forwardSpeed, turnSpeed;

    public DriveValue(double forward, double turn){
        this.forwardSpeed = forward;
        this.turnSpeed = turn;
    }

    public double getForwardSpeed(){
        return forwardSpeed;
    }

    public double getTurnSpeed(){
        return turnSpeed;
    }

    // TODO: Add tank mode support

}