package com.thegongoliers.output;

import com.kylecorry.geometry.Path;
import com.thegongoliers.output.interfaces.DriveTrainInterface;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Kylec on 3/27/2017.
 */
public class DriveUtil {

    /**
     * Return the time to drive a given distance.
     *
     * @param distance      The distance to drive.
     * @param robotMaxSpeed The max speed of the robot.
     * @param targetSpeed   The max speed to drive this distance from 0 to 1 inclusive.
     * @return The time to drive a given distance.
     */
    public double timeToDriveDistanceDeadReckoning(double distance, double robotMaxSpeed, double targetSpeed) {
        double distancePerSecond = robotMaxSpeed * targetSpeed;
        return distance / distancePerSecond;
    }


    /**
     * Drive to a given distance using bang-bang control.
     *
     * @param distance        The distance to drive to.
     * @param currentDistance The current distance travelled (by encoders)
     * @param threshold       The threshold in which to stop driving.
     * @param maxSpeed        The max speed of the robot from 0 to 1 inclusive.
     * @param driveTrain      The drive train of the robot.
     * @return True if the robot is at the target distance within the threshold.
     */
    public boolean bangBangDriveDistance(double distance, double currentDistance, double threshold, double maxSpeed, DriveTrainInterface driveTrain) {
        double error = distance - currentDistance;
        if (error > threshold) {
            driveTrain.forward(maxSpeed);
        } else if (error < -threshold) {
            driveTrain.backward(maxSpeed);
        } else {
            driveTrain.stop();
            return true;
        }
        return false;
    }

    /**
     * Drive along a precomputed trajectory with the given velocity and acceleration using feed forward control.
     *
     * @param velocity     The desired velocity.
     * @param acceleration The desired acceleration.
     * @param kv           The velocity constant (normally 1 / maxVelocity).
     * @param ka           The acceleration constant.
     * @param driveTrain   The drive train of the robot.
     */
    public void feedForwardControl(double velocity, double acceleration, double kv, double ka, DriveTrainInterface driveTrain) {
        driveTrain.forward(kv * velocity + ka * acceleration);
    }


}
