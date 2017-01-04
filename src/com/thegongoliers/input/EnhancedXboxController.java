package com.thegongoliers.input;

import com.thegongoliers.geometry.Point;

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

	private double triggerThreshold = 0.5;

	public EnhancedXboxController(int port) {
		super(port);
	}

	public final Trigger LT = new Trigger() {

		@Override
		public boolean get() {
			return getLeftTrigger() >= triggerThreshold;
		}
	};

	public final Trigger RT = new Trigger() {

		@Override
		public boolean get() {
			return getRightTrigger() >= triggerThreshold;
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

	public Point getLeftStick() {
		return new Point(getLeftX(), getLeftY(), 0);
	}

	public Point getRightStick() {
		return new Point(getRightX(), getRightY(), 0);
	}

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

	public boolean getLB() {
		return getBumper(Hand.kLeft);
	}

	public boolean getRB() {
		return getBumper(Hand.kRight);
	}

	public double getTrigger() {
		return getLeftTrigger() + getRightTrigger();
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

	public void vibrate(float value) {
		setRumble(RumbleType.kLeftRumble, value);
		setRumble(RumbleType.kRightRumble, value);
	}

	public void stopVibration() {
		vibrate(0f);
	}

	public void setTriggerThreshold(double thresh) {
		triggerThreshold = thresh;
	}

	public double getTriggerThreshold() {
		return triggerThreshold;
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
