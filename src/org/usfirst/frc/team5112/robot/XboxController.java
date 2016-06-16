package org.usfirst.frc.team5112.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class XboxController extends Joystick {

	public final JoystickButton A = new JoystickButton(this, 1);
	public final JoystickButton B = new JoystickButton(this, 2);
	public final JoystickButton X = new JoystickButton(this, 3);
	public final JoystickButton Y = new JoystickButton(this, 4);
	public final JoystickButton LB = new JoystickButton(this, 5);
	public final JoystickButton RB = new JoystickButton(this, 6);
	public final JoystickButton BACK = new JoystickButton(this, 7);
	public final JoystickButton START = new JoystickButton(this, 8);
	public final JoystickButton LEFT_STICK_BUTTON = new JoystickButton(this, 9);
	public final JoystickButton RIGHT_STICK_BUTTON = new JoystickButton(this, 10);

	public final Trigger LT = new Trigger() {

		@Override
		public boolean get() {
			return Math.abs(Math.round(getLeftTrigger())) == 1;
		}
	};

	public final Trigger RT = new Trigger() {

		@Override
		public boolean get() {
			return Math.abs(Math.round(getRightTrigger())) == 1;
		}
	};

	public final Trigger DPAD_UP = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == 0;
		}
	};

	public final Trigger DPAD_DOWN = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == 180;
		}
	};

	public final Trigger DPAD_RIGHT = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == 90;
		}
	};

	public final Trigger DPAD_LEFT = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == 270;
		}
	};

	public double getLeftY() {
		return getRawAxis(1);
	}

	public double getLeftX() {
		return getRawAxis(0);
	}

	public double getRightY() {
		return getRawAxis(5);
	}

	public double getRightX() {
		return getRawAxis(4);
	}

	public double getLeftTrigger() {
		return getRawAxis(2);
	}

	public double getRightTrigger() {
		return getRawAxis(3);
	}

	public void rumble(float value) {
		setRumble(RumbleType.kLeftRumble, value);
		setRumble(RumbleType.kRightRumble, value);
	}

	public static final int DPAD_NORTH = 0;
	public static final int DPAD_NORTHEAST = 45;
	public static final int DPAD_EAST = 90;
	public static final int DPAD_SOUTHEAST = 135;
	public static final int DPAD_SOUTH = 180;
	public static final int DPAD_SOUTHWEST = 225;
	public static final int DPAD_WEST = 270;
	public static final int DPAD_NORTHWEST = 315;

	public XboxController(int port) {
		super(port);
	}

}
