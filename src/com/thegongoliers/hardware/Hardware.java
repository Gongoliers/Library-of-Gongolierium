package com.thegongoliers.hardware;

import com.ctre.CANTalon;
import com.thegongoliers.input.CurrentSensor;
import com.thegongoliers.input.Switch;
import com.thegongoliers.input.TiltSensor;
import com.thegongoliers.input.camera.CameraInterface;
import com.thegongoliers.output.JoinedSpeedController;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import com.thegongoliers.input.camera.MicrosoftLifeCam;

public class Hardware {

	public static class Cameras {
		public static CameraInterface microsoftLifeCam(int port) {
			return new MicrosoftLifeCam(port);
		}
	}

	public static class Accelerometers {
		public static Accelerometer builtin() {
			return new BuiltInAccelerometer();
		}
	}

	public static class Switches {
		public static Switch limitSwitch(int port) {
			return new DigitalInput(port)::get;
		}

		public static Switch normallyOpen(int port) {
			DigitalInput input = new DigitalInput(port);
			return input::get;
		}

		public static Switch normallyClosed(int port) {
			DigitalInput input = new DigitalInput(port);
			return () -> !input.get();
		}

		public static Switch current(CurrentSensor sensor, double thresh) {
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
		public static Gyro gyroscope(int port) {
			AnalogGyro gyro = new AnalogGyro(port);
			gyro.initGyro();
			return gyro;
		}

		public static Encoder encoder(int port1, int port2, double distancePerPulse) {
			Encoder encoder = new Encoder(port1, port2);
			encoder.setDistancePerPulse(distancePerPulse);
			return encoder;
		}

		public static TiltSensor tilt(Accelerometer accel) {
			return new TiltSensor(accel);
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
		public static Encoder encoder(int port1, int port2, double distancePerPulse) {
			return AngleSensors.encoder(port1, port2, distancePerPulse);
		}
	}

	public static class Pneumatics {
		public static Solenoid solenoid(int port) {
			return new Solenoid(port);
		}

		public static Compressor compressor() {
			return new Compressor();
		}

		public static Compressor compressor(int port) {
			return new Compressor(port);
		}
	}

}
