package com.thegongoliers.input.joystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Adapted from Josh Pordon's gist on GitHub.
 * https://gist.github.com/pordonj/970b2c189cc6ee06388b3e2f12abcb72
 */
public class DPadButton extends Button {

    private final Joystick mJoystick;
    private final Direction mDirection;

    public DPadButton(Joystick joystick, Direction direction) {
        this.mJoystick = joystick;
        this.mDirection = direction;
    }

    public enum Direction {
        UP(0), RIGHT(90), DOWN(180), LEFT(270);
        int direction;

        Direction(int direction) {
            this.direction = direction;
        }
    }

    public boolean get() {
        int dPadValue = mJoystick.getPOV();
        return (dPadValue == mDirection.direction) || (dPadValue == (mDirection.direction + 45) % 360) || (dPadValue == (mDirection.direction + 315) % 360);
    }

}
