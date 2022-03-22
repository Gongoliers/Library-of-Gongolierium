package com.thegongoliers.commands.vision;

import com.thegongoliers.input.vision.LimelightCamera;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SetLimelightPipelineCommand extends InstantCommand {
    public SetLimelightPipelineCommand(Subsystem subsystem, LimelightCamera camera, int pipeline) {
        super(() -> camera.selectPipeline(pipeline), subsystem);
    }
}
