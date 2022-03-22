package com.thegongoliers.commands.vision;

import com.thegongoliers.input.vision.LimelightCamera;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SetLimelightTargetingModeCommand extends InstantCommand {

    public SetLimelightTargetingModeCommand(Subsystem subsystem, LimelightCamera camera, boolean targeting) {
        super(() -> {
            if (targeting) {
                camera.switchToTargetingMode();
            } else {
                camera.switchToDriverMode();
            }
        }, subsystem);
    }

}
