package com.thegongoliers.hardware;

import com.ctre.CANTalon;
import com.thegongoliers.input.AngleSensor;
import com.thegongoliers.input.CurrentSensor;
import com.thegongoliers.input.DistanceSensor;
import com.thegongoliers.input.Gyroscope;
import com.thegongoliers.input.Switch;
import com.thegongoliers.input.ThreeAxisAccelerometer;
import com.thegongoliers.input.camera.Camera;
import com.thegongoliers.input.camera.Camera.LEDColor;
import com.thegongoliers.output.JoinedSpeedController;
import com.thegongoliers.output.Solenoid;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

import com.thegongoliers.input.camera.MicrosoftLifeCam;

public class Hardware {

	public static class Cameras {
		public static Camera microsoftLifeCam(int port) {
			return microsoftLifeCam(port, LEDColor.GREEN);
		}

		public static Camera microsoftLifeCam(int port, LEDColor color) {
			return new Camera.CameraBuilder().setCamera(new MicrosoftLifeCam(port)).setLEDColor(color).build();
		}
	}

	public static class Accelerometers {
		public static ThreeAxisAccelerometer builtin() {
			BuiltInAccelerometer accel = new BuiltInAccelerometer();
			return ThreeAxisAccelerometer.create(accel::getX, accel::getY, accel::getZ);
		}
	}

	public static class Switches {
		public static Switch normallyOpen(int port) {
			DigitalInput input = new DigitalInput(port);
			return input::get;
		}

		public static Switch normallyClosed(int port) {
			DigitalInput input = new DigitalInput(port);
			return () -> !input.get();
		}

		public static Switch currentSwitch(CurrentSensor sensor, double thresh) {
			return () -> sensor.getCurrent() >= thresh;
		}
	}

	public static class Motors {
		public static SpeedController join(SpeedController... controllers) {
			return new JoinedSpeedController(controllers);
		}

		public static SpeedController talon(int port) {
			return new Talon(port);
		}

		public static SpeedController CANTalon(int port) {
			return new CANTalon(port);
		}

		public static SpeedController jaguar(int port) {
			return new Jaguar(port);
		}

		public static SpeedController victor(int port) {
			return new Victor(port);
		}
	}

	public static class AngleSensors {
		public static Gyroscope gyroscope(int port) {
			AnalogGyro gyro = new AnalogGyro(port);
			gyro.initGyro();
			return Gyroscope.create(gyro::getAngle, gyro::getRate);
		}

		public static AngleSensor encoder(int port1, int port2, double distancePerPulse) {
			Encoder encoder = new Encoder(port1, port2);
			encoder.setDistancePerPulse(distancePerPulse);
			return AngleSensor.create(encoder::getDistance);
		}
	}

	public static class CurrentSensors {
		public static CurrentSensor pdpCurrentSensor(int port) {
			PowerDistributionPanel pdp = new PowerDistributionPanel();
			return () -> pdp.getCurrent(port);
		}

		public static CurrentSensor pdpTotalCurrentSensor() {
			PowerDistributionPanel pdp = new PowerDistributionPanel();
			return pdp::getTotalCurrent;
		}
	}

	public static class DistanceSensors {
		public static DistanceSensor encoder(int port1, int port2, double distancePerPulse) {
			Encoder encoder = new Encoder(port1, port2);
			encoder.setDistancePerPulse(distancePerPulse);
			return DistanceSensor.create(encoder::getDistance);
		}
	}

	public static class Solenoids {
		public static Solenoid solenoid(int port) {
			edu.wpi.first.wpilibj.Solenoid solenoid = new edu.wpi.first.wpilibj.Solenoid(0);
			return new Solenoid() {

				@Override
				public Solenoid retract() {
					solenoid.set(false);
					return this;
				}

				@Override
				public boolean isRetracted() {
					return !solenoid.get();
				}

				@Override
				public boolean isExtended() {
					return solenoid.get();
				}

				@Override
				public Solenoid extend() {
					solenoid.set(true);
					return this;
				}
			};
		}
	}

}
