package com.thegongoliers.hardware;

import com.thegongoliers.input.switches.ResettableSwitch;
import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.input.voltage.IVoltageSensor;
import com.thegongoliers.math.GMath;
import com.thegongoliers.output.interfaces.Drivetrain;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import java.util.function.BooleanSupplier;

/**
 * Miscellaneous hardware functions.
 */
public class Hardware {

	/**
     * Combines multiple buttons into a single button.
     * @param button1 The first button.
     * @param buttons The rest of the buttons.
     * @return A Button which is pressed only when ALL of the buttons are pressed. 
     */
    public static Button combineButtons(Button button1, Button... buttons){
        return new Button(){
            @Override
            public boolean get() {
                for (Button button: buttons){
                    if (!button.get()){
                        return false;
                    }
                }
                return button1.get();
            }
        };
    }

	/**
	 * Creates a button from a boolean supplier.
	 * @param booleanSupplier The boolean supplier to become a button.
	 * @return The button which is pressed when the boolean supplier is true.
	 */
	public static Button makeButton(BooleanSupplier booleanSupplier){
		return new Button() {
			@Override
			public boolean get() {
				return booleanSupplier.getAsBoolean();
			}
		};
	}

	/**
	 * Creates a button from a trigger.
	 * @param trigger The trigger to become a button.
	 * @return The button which is pressed when the trigger is triggered.
	 */
	public static Button triggerToButton(Trigger trigger){
		return Hardware.makeButton(trigger::get);
	}

	/**
	 * Creates a trigger from a switch.
	 * @param aSwitch The switch to become a trigger.
	 * @return The trigger which is triggered when the switch is triggered.
	 */
	public static Trigger switchToTrigger(Switch aSwitch){
		return Hardware.makeTrigger(aSwitch::isTriggered);
	}

	/**
	 * Creates a trigger from a boolean supplier.
	 * @param booleanSupplier The boolean supplier to become a trigger.
	 * @return The trigger which is triggered when the boolean supplier is true.
	 */
	public static Trigger makeTrigger(BooleanSupplier booleanSupplier){
		return new Trigger() {
			@Override
			public boolean get() {
				return booleanSupplier.getAsBoolean();
			}
		};
	}

	/**
	 * Creates a switch from a boolean supplier.
	 * @param booleanSupplier The boolean supplier to become a switch.
	 * @return The switch which is triggered when the boolean supplier is true.
	 */
	public static Switch makeSwitch(BooleanSupplier booleanSupplier){
		return booleanSupplier::getAsBoolean;
	}

	/**
	 * Creates a resettable switch from a switch.
	 * @param aSwitch The switch to become a resettable switch.
	 * @return The resettable switch, which will remain true if the switch becomes triggered.
	 */
	public static ResettableSwitch makeResettableSwitch(Switch aSwitch){
		return new ResettableSwitch() {

			private boolean wasTriggered = false;

			@Override
			public void reset() {
				wasTriggered = false;
			}

			@Override
			public boolean isTriggered() {
				wasTriggered |= aSwitch.isTriggered();
				return wasTriggered;
			}
		};
	}

	/**
	 * Invert the direction of the Gyro.
	 * 
	 * @param gyro
	 *            The Gyro to invert.
	 * @return The inverted Gyro.
	 */
	public static Gyro invertGyro(Gyro gyro) {
		return new Gyro() {

			@Override
			public void close() throws Exception {
				gyro.close();
			}

			@Override
			public void reset() {
				gyro.reset();
			}

			@Override
			public double getRate() {
				return -gyro.getRate();
			}

			@Override
			public double getAngle() {
				return -gyro.getAngle();
			}

			@Override
			public void calibrate() {
				gyro.calibrate();
			}
		};
	}

	/**
	 * Inverts a potentiometer. If a potentiometer goes from 0 to -100 degrees (after scale and zero point), but should be 0 to 100, use this method. 
	 * @param potentiometer The potentiometer to invert.
	 * @return The inverted potentiometer. 
	 */
	public static Potentiometer invertPotentiometer(Potentiometer potentiometer){
		return new Potentiometer(){
		
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				potentiometer.setPIDSourceType(pidSource);
			}
		
			@Override
			public double pidGet() {
				return -potentiometer.pidGet();
			}
		
			@Override
			public PIDSourceType getPIDSourceType() {
				return potentiometer.getPIDSourceType();
			}
		
			@Override
			public double get() {
				return -potentiometer.get();
			}
		};
	}

	/**
	 * Creates potentiometer.
	 * @param port The analog port that the potentiometer is plugged into.
	 * @param degreeRange The total range in degrees that the potentiometer can turn
     * @param zeroPoint The zero point of the potentiometer. If you expect a position to be 0, but it is reading X degrees, put X here. (ex. actual 1000 to 1100 degrees, expected 0 to 100 degrees. Put 1000 here.)
	 * @return A potentiometer.
     */
	public static Potentiometer createPotentiometer(int port, double degreeRange, double zeroPoint){
        return new AnalogPotentiometer(port, degreeRange, -zeroPoint);
    }

	/**
	 * Creates a 10 turn potentiometer as sold by Andymark.
	 * @param port The analog port that the potentiometer is plugged into.
     * @param zeroPoint The zero point of the potentiometer. If you expect a position to be 0, but it is reading X degrees, put X here. (ex. actual 1000 to 1100 degrees, expected 0 to 100 degrees. Put 1000 here.)
	 * @return A 10 turn potentiometer.
     */
	public static Potentiometer create10TurnPotentiometer(int port, double zeroPoint){
        return createPotentiometer(port, 3600, -zeroPoint);
    }

    public static Drivetrain createDrivetrain(DifferentialDrive robotDrive){
        return new Drivetrain(){
        
            @Override
            public void stop() {
                robotDrive.stopMotor();
            }
        
            @Override
            public void tank(double leftSpeed, double rightSpeed) {
                robotDrive.tankDrive(leftSpeed, rightSpeed, false);
            }
        
            @Override
            public void arcade(double speed, double turn) {
                robotDrive.arcadeDrive(speed, turn, false);
            }
        };
    }

	/**
	 * Convert a voltage to a PWM signal (percent power) based on the current voltage of the battery
	 * @param voltage the desired voltage
	 * @param batteryVoltageSensor the battery voltage sensor
	 * @return the PWM signal [-1, 1] which delivers the desired voltage
	 */
    public static double voltageToPWM(double voltage, IVoltageSensor batteryVoltageSensor){
    	if (batteryVoltageSensor == null || batteryVoltageSensor.getVoltage() == 0){
    		return 0;
		}
    	double rawPWM = voltage / batteryVoltageSensor.getVoltage();
		return GMath.clamp(rawPWM, -1, 1);
	}

}
