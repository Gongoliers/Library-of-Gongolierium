package com.thegongoliers.paths;

public class PathStep {

    private PathStepType type;
    private double value;

    private PathStep(double value, PathStepType type){
        this.type = type;
        this.value = value;
    }

    public static PathStep rotation(double angle){
        return new PathStep(angle, PathStepType.ROTATION);
    } 

    public static PathStep straightAway(double distance){
        return new PathStep(distance, PathStepType.STRAIGHT_AWAY);
    } 

    public double getValue(){
        return value;
    }

    public PathStepType getType(){
        return type;
    }

}