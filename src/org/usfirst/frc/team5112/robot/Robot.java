
package org.usfirst.frc.team5112.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;

import org.usfirst.frc.team5112.robot.Camera.LEDColor;
import org.usfirst.frc.team5112.robot.Camera.Target;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {

	private Camera cam;

	public Robot() {
		cam = new Camera.CameraBuilder().setCamera(new MicrosoftLifeCam("cam0")).setLEDColor(LEDColor.GREEN)
				.setNormalBrightness(50).build();

	}

	public void robotInit() {
	}

	public void autonomous() {
		cam.enableTargetMode();
		while (isAutonomous()) {
			cam.display();
			Target target = cam.findTarget(12, 5);
			SmartDashboard.putNumber("Angle", target.getAngle());
		}

	}

	/**
	 * Runs the motors with arcade steering.
	 */
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {

			Timer.delay(0.005); // wait for a motor update time
		}
	}

	/**
	 * Runs during test mode
	 */
	public void test() {
	}
}
