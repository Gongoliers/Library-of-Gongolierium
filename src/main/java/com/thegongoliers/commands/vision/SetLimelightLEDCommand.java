package com.thegongoliers.commands.vision;

import com.thegongoliers.input.vision.LimelightCamera;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SetLimelightLEDCommand extends InstantCommand {
    public SetLimelightLEDCommand(Subsystem subsystem, LimelightCamera camera, LimelightCamera.LEDMode mode) {
        super(() -> camera.setLEDMode(mode), subsystem);
    }
}
