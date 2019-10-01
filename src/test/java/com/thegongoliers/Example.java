package com.thegongoliers;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.output.drivemodules.DeadbandModule;
import com.thegongoliers.output.drivemodules.PowerEfficiencyModule;
import com.thegongoliers.output.drivemodules.PrecisionModule;
import com.thegongoliers.output.drivemodules.SpeedConstraintModule;
import com.thegongoliers.output.drivemodules.StabilityModule;
import com.thegongoliers.output.subsystems.ModularDrivetrain;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

class Example {
    public static void main(String[] args) {

        // Create a low-level drivetrain component from motors
        DifferentialDrive lowLevelDrivetrain = new DifferentialDrive(new Talon(0), new Talon(1));

        // Create a modular drivetrain from the low-level drivetrain
        ModularDrivetrain drivetrain = ModularDrivetrain.from(lowLevelDrivetrain);

        // Add a stability module to the drivetrain to ensure the robot will drive straight
        drivetrain.addModule(new StabilityModule(new AnalogGyro(0), 0.02, 0.5));

        // Add a precision module to ensure the robot has fine control at low speeds
        drivetrain.addModule(new PrecisionModule(0.5, 0.5));

        // Add a power efficiency module to ensure the robot uses power wisely when accelerating
        drivetrain.addModule(new PowerEfficiencyModule(0.1, 0.1)); // Takes 0.1 seconds to go from 0 to full speed

        // Add a speed constraint module to keep the robot's max speed in check, don't scale the inputs
        drivetrain.addModule(new SpeedConstraintModule(0.9, 0.9, false));

        // Add a deadband module to prevent the robot from moving when the joystick should be centered
        // The -1 (the run order) ensures that the deadband will be run before any other module
        drivetrain.addModule(new DeadbandModule(0.01, 0.01), -1);


        // In a subsystem / somewhere else in the code

        // Drive the robot in arcade mode, all modules will be run to determine what speeds to set the motors
        drivetrain.arcade(1.0, 0.4);
    }
}