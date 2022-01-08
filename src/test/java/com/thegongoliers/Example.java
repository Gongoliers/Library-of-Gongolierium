package com.thegongoliers;

import com.thegongoliers.output.drivetrain.*;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

class Example {
    public static void main(String[] args) {

        // Create a low-level drivetrain component from motors
        DifferentialDrive lowLevelDrivetrain = new DifferentialDrive(new Talon(0), new Talon(1));

        // Create a modular drivetrain from the low-level drivetrain
        ModularDrivetrain drivetrain = ModularDrivetrain.from(lowLevelDrivetrain);

        // Install the drive modules
        drivetrain.setModules(
            new PrecisionModule(0.5),
            new PowerEfficiencyModule(0.1),
            new StabilityModule(new AnalogGyro(0), 0.02, 0.5),
            new SpeedConstraintModule(0.9, false)
        );

        // In a subsystem / somewhere else in the code

        // Drive the robot in arcade mode, all modules will be run to determine what speeds to set the motors
        drivetrain.arcade(1.0, 0.4);
    }
}