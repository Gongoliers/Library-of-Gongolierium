package com.thegongoliers.examples;

import com.thegongoliers.output.Piston;
import com.thegongoliers.subsystems.RobotSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GripperSubsystem extends RobotSubsystem {

    private Piston piston;

    public GripperSubsystem(Piston piston){
        this.piston = piston;
    }

    @Override
    public void publish() {
        SmartDashboard.putBoolean("Gripper closed", isClosed());
    }

    @Override
    public void initialize() {
        piston.retract();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void open(){
        piston.retract();
    }

    public void close(){
        piston.extend();
    }

    public boolean isOpened(){
        return piston.isRetracted();
    }

    public boolean isClosed(){
        return piston.isExtended();
    }
}
