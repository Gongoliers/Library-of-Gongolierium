package com.thegongoliers.output;

import com.thegongoliers.input.EnhancedXboxController;
import com.thegongoliers.output.interfaces.MecanumDriveTrainInterface;

import com.thegongoliers.output.interfaces.Relay;
import com.thegongoliers.output.interfaces.Stoppable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Create a stabilized robot drive, which is a robot drive with a gyro.
 */
public class StabilizedRobotDrive implements Stoppable, MecanumDriveTrainInterface {

    private double relativeAngle = 0;
    private Gyro gyro;
    private double kp = 0.03;
    private RobotDrive robotDrive;

    /**
     * Create a stabilized robot drive.
     *
     * @param frontLeftMotor  The front left motor controller.
     * @param rearLeftMotor   The rear left motor controller.
     * @param frontRightMotor The front right motor controller.
     * @param rearRightMotor  The rear right motor controller.
     * @param gyro            The gyroscope on the robot.
     */
    public StabilizedRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor,
                                SpeedController rearRightMotor, Gyro gyro) {
        robotDrive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
        this.gyro = gyro;
    }

    /**
     * Create a stabilized robot drive.
     *
     * @param leftMotor  The left motor controller.
     * @param rightMotor The right motor controller.
     * @param gyro       The gyroscope on the robot.
     */
    public StabilizedRobotDrive(SpeedController leftMotor, SpeedController rightMotor, Gyro gyro) {
        robotDrive = new RobotDrive(leftMotor, rightMotor);
        this.gyro = gyro;
    }

    /**
     * Mark the current orientation as the zero point for stabilization.
     */
    public void markRelativeOrientation() {
        relativeAngle = getOrientation();
    }

    /**
     * Get the current orientation of the robot in the range [0, 360).
     *
     * @return The orientation of the robot.
     */
    public double getFieldOrientation() {
        return getOrientation() % 360;
    }

    private double getOrientation() {
        if (gyro != null)
            return gyro.getAngle();
        return 0;
    }

    /**
     * Rotate in the direction of the given angle (in range [0, 360)) using the
     * PID controller.
     *
     * @param angle      The target orientation.
     * @param controller The PID controller for rotating.
     * @return True if the robot is at the target angle according to the PID
     * controller threshold.
     */
    public boolean rotateToward(double angle, PID controller) {
        if (gyro != null) {
            controller.continuous = true;
            controller.minInput = 0;
            controller.maxInput = 360;
            arcade(0, controller.getOutput(getFieldOrientation(), angle));
            return controller.isAtTargetPosition(getFieldOrientation(), angle);
        } else {
            return false;
        }
    }

    public void mecanum(double x, double y, double rotation) {
        robotDrive.mecanumDrive_Cartesian(x, y, rotation, 0);
    }

    public void arcade(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void tank(double left, double right) {
        robotDrive.tankDrive(left, right);
    }

    public void forward(double speed) {
        arcade(-speed, kp * (relativeAngle - getOrientation()));
    }

    public void backward(double speed) {
        arcade(speed, kp * (relativeAngle - getOrientation()));
    }

    public void strafeLeft(double speed) {
        mecanum(-speed, 0, kp * (relativeAngle - getOrientation()));
    }

    public void strafeRight(double speed) {
        mecanum(speed, 0, kp * (relativeAngle - getOrientation()));
    }

    public void rotateLeft(double speed) {
        arcade(0, -speed);
    }

    public void rotateRight(double speed) {
        arcade(0, speed);
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    /**
     * Drive the robot with an arcade control, but when not rotating the robot
     * will stabilize itself.
     *
     * @param speed             The speed of the robot from -1 to 1 inclusive. Negative is
     *                          forward.
     * @param rotation          The rotation of the robot from -1 to 1 inclusive. Negative is
     *                          left.
     * @param rotationThreshold The rotation threshold in which to kick in the stabilization.
     */
    public void arcadeStabilized(double speed, double rotation, double rotationThreshold) {
        if (Math.abs(rotation) >= rotationThreshold) {
            arcade(speed, rotation);
            markRelativeOrientation();
        } else {
            arcade(speed, kp * (relativeAngle - getOrientation()));
        }
    }

    /**
     * Drive the robot with a mecanum control, but when not rotating the robot
     * will stabilize itself.
     *
     * @param x                 The x speed of the robot from -1 to 1 inclusive. Negative is
     *                          left.
     * @param y                 The y speed of the robot from -1 to 1 inclusive. Negative is
     *                          forward.
     * @param rotation          The rotation of the robot from -1 to 1 inclusive. Negative is
     *                          left.
     * @param rotationThreshold The rotation threshold in which to kick in the stabilization.
     */
    public void mecanumStabilized(double x, double y, double rotation, double rotationThreshold) {
        if (Math.abs(rotation) >= rotationThreshold) {
            mecanum(x, y, rotation);
            markRelativeOrientation();
        } else {
            mecanum(x, y, kp * (relativeAngle - getOrientation()));
        }
    }

    /**
     * Drive the robot with an arcade control, but when not rotating the robot
     * will stabilize itself.
     *
     * @param speed    The speed of the robot from -1 to 1 inclusive. Negative is
     *                 forward.
     * @param rotation The rotation of the robot from -1 to 1 inclusive. Negative is
     *                 left.
     */
    public void arcadeStabilized(double speed, double rotation) {
        arcadeStabilized(speed, rotation, 0.1);
    }

    /**
     * Drive the robot using an arcade drive with the Y and Z (twist) axes of
     * the joystick.
     *
     * @param joystick The joystick to drive with.
     */
    public void arcadeYZ(Joystick joystick) {
        arcade(joystick.getY(), joystick.getZ());
    }

    /**
     * Drive the robot using an arcade drive with the Y and X axes of the
     * joystick.
     *
     * @param joystick The joystick to drive with.
     */
    public void arcadeYX(Joystick joystick) {
        arcade(joystick.getY(), joystick.getX());
    }

    /**
     * Drive the robot using a typical FPS style where speed is determined by
     * the left stick Y and rotation is controlled by the right stick X.
     *
     * @param xbox The Xbox controller to drive with.
     */
    public void arcadeFPS(EnhancedXboxController xbox) {
        arcade(xbox.getLeftY(), xbox.getRightX());
    }

    /**
     * Drive the robot using a typical racing style where speed is determined by
     * the trigger values (left trigger is reverse, right trigger is forward)
     * and rotation is controlled by the left stick X.
     *
     * @param xbox The Xbox controller to drive with.
     */
    public void arcadeRacing(EnhancedXboxController xbox) {
        arcade(xbox.getTrigger(), xbox.getLeftX());
    }

    /**
     * Drive the robot using a mecanum drive with the Y, X, and Z (twist) axes
     * of the joystick.
     *
     * @param joystick The joystick to drive with.
     */
    public void mecanumXYZ(Joystick joystick) {
        mecanum(joystick.getX(), joystick.getY(), joystick.getZ());
    }

    /**
     * Drive the robot using a typical FPS style where y speed is determined by
     * the left stick Y, x speed is determined by the left stick X, and rotation
     * is controlled by the right stick X.
     *
     * @param xbox The Xbox controller to drive with.
     */
    public void mecanumFPS(EnhancedXboxController xbox) {
        mecanum(xbox.getLeftX(), xbox.getLeftY(), xbox.getRightX());
    }

    /**
     * Drive the robot using tank drive with two joysticks.
     *
     * @param left  The left side joystick.
     * @param right The right side joystick.
     */
    public void tank(Joystick left, Joystick right) {
        tank(left.getY(), right.getY());
    }

    /**
     * Drive the robot using tank drive with a single Xbox controller. The left
     * stick Y controls the left speed, and the right stick Y controls the right
     * speed.
     *
     * @param xbox The xbox controller to drive with.
     */
    public void tank(EnhancedXboxController xbox) {
        tank(xbox.getLeftY(), xbox.getRightY());
    }
}
