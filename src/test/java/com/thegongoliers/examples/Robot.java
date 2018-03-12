package com.thegongoliers.examples;

import com.thegongoliers.subsystems.SubsystemManager;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    private SubsystemManager subsystemManager =
            new SubsystemManager(GyroSubsystem.getInstance());


    @Override
    public void robotInit() {
        super.robotInit();
        subsystemManager.initialize();
        subsystemManager.publish();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        subsystemManager.publish();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
        subsystemManager.publish();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
        subsystemManager.publish();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
        subsystemManager.publish();
    }
}
