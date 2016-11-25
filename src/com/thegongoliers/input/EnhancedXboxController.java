package com.thegongoliers.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class EnhancedXboxController extends XboxController {

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

	public EnhancedXboxController(int port) {
		super(port);
	}

	public final Trigger LT = new Trigger() {

		@Override
		public boolean get() {
			return getTrigger(Hand.kLeft);
		}
	};

	public final Trigger RT = new Trigger() {

		@Override
		public boolean get() {
			return getTrigger(Hand.kRight);
		}
	};

	public final Trigger DPAD_UP = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.NORTH.getDirection();
		}
	};

	public final Trigger DPAD_DOWN = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.SOUTH.getDirection();
		}
	};

	public final Trigger DPAD_RIGHT = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.EAST.getDirection();
		}
	};

	public final Trigger DPAD_LEFT = new Trigger() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.WEST.getDirection();
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
		return getTriggerAxis(Hand.kLeft);
	}

	public double getRightTrigger() {
		return getTriggerAxis(Hand.kRight);
	}

	public DPAD getPOVDirection() {
		return DPAD.values()[getPOV() / 45];
	}

	public void rumble(float value) {
		setRumble(RumbleType.kLeftRumble, value);
		setRumble(RumbleType.kRightRumble, value);
	}

	public static enum DPAD {
		NORTH(0), NORTHEAST(45), EAST(90), SOUTHEAST(135), SOUTH(180), SOUTHWEST(225), WEST(270), NORTHWEST(315);

		private final int direction;

		DPAD(int direction) {
			this.direction = direction;
		}

		public int getDirection() {
			return direction;
		}
	}

}
