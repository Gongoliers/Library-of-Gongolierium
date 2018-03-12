package com.thegongoliers.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsystemManager {

    private List<RobotSubsystem> subsystems;

    public SubsystemManager(List<RobotSubsystem> subsystems){
        if(subsystems == null){
            subsystems = new ArrayList<>();
        }

        this.subsystems = subsystems;
    }

    public SubsystemManager(RobotSubsystem... subsystems){
        this(Arrays.asList(subsystems));
    }

    public void initialize(){
        for (RobotSubsystem s: subsystems) {
            s.initialize();
        }
    }

    public void publish(){
        for (RobotSubsystem s: subsystems) {
            s.publish();
        }
    }

}
