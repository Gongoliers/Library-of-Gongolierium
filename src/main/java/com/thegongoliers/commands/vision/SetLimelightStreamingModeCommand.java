package com.thegongoliers.commands.vision;

import com.thegongoliers.input.vision.LimelightCamera;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SetLimelightStreamingModeCommand extends InstantCommand {
    public SetLimelightStreamingModeCommand(Subsystem subsystem, LimelightCamera camera, LimelightCamera.StreamingMode mode) {
        super(() -> camera.setStreamingMode(mode), subsystem);
    }
}
