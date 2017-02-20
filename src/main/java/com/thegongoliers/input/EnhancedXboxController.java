package com.thegongoliers.input;

import com.kylecorry.geometry.Point;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class EnhancedXboxController extends XboxController {

	public final Button A = new JoystickButton(this, 1);
	public final Button B = new JoystickButton(this, 2);
	public final Button X = new JoystickButton(this, 3);
	public final Button Y = new JoystickButton(this, 4);
	public final Button LB = new JoystickButton(this, 5);
	public final Button RB = new JoystickButton(this, 6);
	public final Button BACK = new JoystickButton(this, 7);
	public final Button START = new JoystickButton(this, 8);
	public final Button LEFT_STICK_BUTTON = new JoystickButton(this, 9);
	public final Button RIGHT_STICK_BUTTON = new JoystickButton(this, 10);

	private double triggerThreshold = 0.5;

	/**
	 * An Xbox controller with its buttons defined as instances of the Button
	 * class.
	 * 
	 * @param port
	 *            The port which the controller is plugged into.
	 */
	public EnhancedXboxController(int port) {
		super(port);
	}

	/**
	 * The left trigger.
	 */
	public final Button LT = new Button() {

		@Override
		public boolean get() {
			return getLeftTrigger() >= triggerThreshold;
		}
	};

	/**
	 * The right trigger.
	 */
	public final Button RT = new Button() {

		@Override
		public boolean get() {
			return getRightTrigger() >= triggerThreshold;
		}
	};

	public final Button DPAD_UP = new Button() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.NORTH.getDirection();
		}
	};

	public final Button DPAD_DOWN = new Button() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.SOUTH.getDirection();
		}
	};

	public final Button DPAD_RIGHT = new Button() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.EAST.getDirection();
		}
	};

	public final Button DPAD_LEFT = new Button() {

		@Override
		public boolean get() {
			return getPOV() == DPAD.WEST.getDirection();
		}
	};

	/**
	 * Get the Point representation of the left stick.
	 * 
	 * @return The Point containing the left stick x and y.
	 */
	public Point getLeftStick() {
		return new Point(getLeftX(), getLeftY(), 0);
	}

	/**
	 * Get the Point representation of the right stick.
	 * 
	 * @return The Point containing the right stick x and y.
	 */
	public Point getRightStick() {
		return new Point(getRightX(), getRightY(), 0);
	}

	/**
	 * Get the left stick y value.
	 * 
	 * @return The left stick y value.
	 */
	public double getLeftY() {
		return getRawAxis(1);
	}

	/**
	 * Get the left stick x value.
	 * 
	 * @return The left stick x value.
	 */
	public double getLeftX() {
		return getRawAxis(0);
	}

	/**
	 * Get the right stick y value.
	 * 
	 * @return The right stick y value.
	 */
	public double getRightY() {
		return getRawAxis(5);
	}

	/**
	 * Get the right stick x value.
	 * 
	 * @return The right stick x value.
	 */
	public double getRightX() {
		return getRawAxis(4);
	}

	/**
	 * Get the state of the left bumper.
	 * 
	 * @return The state of the left bumper.
	 */
	public boolean getLB() {
		return getBumper(Hand.kLeft);
	}

	/**
	 * Get the state of the right bumper.
	 * 
	 * @return The state of the right bumper.
	 */
	public boolean getRB() {
		return getBumper(Hand.kRight);
	}

	/**
	 * Get the combined value of the triggers, which is right trigger - left
	 * trigger.
	 * 
	 * @return The combined trigger value.
	 */
	public double getTrigger() {
		return getRightTrigger() - getLeftTrigger();
	}

	/**
	 * Get the value of the left trigger from 0 to 1 inclusive.
	 * 
	 * @return The value of the left trigger.
	 */
	public double getLeftTrigger() {
		return getTriggerAxis(Hand.kLeft);
	}

	/**
	 * Get the value of the right trigger from 0 to 1 inclusive.
	 * 
	 * @return The value of the right trigger.
	 */
	public double getRightTrigger() {
		return getTriggerAxis(Hand.kRight);
	}

	/**
	 * Get the current direction of the DPAD.
	 * 
	 * @return The current direction of the DPAD.
	 */
	public DPAD getPOVDirection() {
		return DPAD.values()[getPOV() / 45];
	}

	/**
	 * Vibrate the controller.
	 * 
	 * @param value
	 *            The intensity of the vibration from 0 to 1 inclusive.
	 */
	public void vibrate(float value) {
		setRumble(RumbleType.kLeftRumble, value);
		setRumble(RumbleType.kRightRumble, value);
	}

	/**
	 * Stop the vibration of the controller.
	 */
	public void stopVibration() {
		vibrate(0f);
	}

	/**
	 * Set the threshold of the trigger at which point it is considered pressed.
	 * 
	 * @param thresh
	 *            The threshold of the trigger from 0 to 1 inclusive.
	 */
	public void setTriggerThreshold(double thresh) {
		triggerThreshold = thresh;
	}

	/**
	 * Get the threshold of the trigger at which point it is considered pressed.
	 */
	public double getTriggerThreshold() {
		return triggerThreshold;
	}

	/**
	 * The direction of the DPAD.
	 *
	 */
	public static enum DPAD {
		NORTH(0), NORTHEAST(45), EAST(90), SOUTHEAST(135), SOUTH(180), SOUTHWEST(225), WEST(270), NORTHWEST(315);

		private final int direction;

		DPAD(int direction) {
			this.direction = direction;
		}

		/**
		 * Get the direction of the DPAD in degrees.
		 * 
		 * @return The direction.
		 */
		public int getDirection() {
			return direction;
		}
	}

}
