package com.thegongoliers.paths;

import java.util.LinkedList;
import java.util.List;

public class SimplePath {

    private List<PathStep> steps;

    public SimplePath(List<PathStep> steps){
        this.steps = new LinkedList<>(steps);
    }

    public SimplePath(){
        this(new LinkedList<>());
    }

    public void addStep(PathStep step){
        steps.add(step);
    }

    public void addRotation(double degrees){
        addStep(PathStep.rotation(degrees));
    }

    public void addStraightAway(double distance){
        addStep(PathStep.straightAway(distance));
    }

    public List<PathStep> getSteps(){
        return steps;
    }

}