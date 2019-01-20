package com.thegongoliers.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command to vibrate an Xbox controller.
 */
public class VibrateXboxController extends Command {

    private XboxController controller;
    private double intensity;
    private GenericHID.RumbleType rumbleType;

    /**
     * Creates a command to vibrate an Xbox controller.
     * @param controller The Xbox controller to vibrate.
     * @param rumbleType The side to vibrate.
     * @param intensity The intensity of the vibration (0 to 1).
     * @param time The time in seconds to vibrate.
     */
    public VibrateXboxController(XboxController controller, GenericHID.RumbleType rumbleType, double intensity, double time){
        this.controller = controller;
        this.rumbleType = rumbleType;
        this.intensity = intensity;
        setTimeout(time);
    }

    @Override
    protected void execute() {
        controller.setRumble(rumbleType, intensity);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        controller.setRumble(rumbleType, 0);
    }
}
