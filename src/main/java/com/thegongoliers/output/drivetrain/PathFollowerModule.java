package com.thegongoliers.output.drivetrain;

import java.util.List;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;
import com.thegongoliers.paths.SimplePath;
import com.thegongoliers.paths.PathStep;
import com.thegongoliers.paths.PathStepType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will follow a path when activated
 */
@Untested
public class PathFollowerModule extends BaseDriveModule {

    /**
     * The gyro used to determine the rotation of the drivetrain
     * Type: edu.wpi.first.wpilibj.interfaces.Gyro
     */
    public static final String VALUE_GYRO = "gyro";

    /**
     * The encoders used to determine the distance traveled by the drivetrain
     * Type: java.util.List<edu.wpi.first.wpilibj.Encoder>
     */
    public static final String VALUE_ENCODERS = "encoders";

    /**
     * The forward strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_FORWARD_STRENGTH = "forward_strength";

    /**
     * The turn strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_TURN_STRENGTH = "turn_strength";

    /**
     * The forward threshold to accept as within range of the distance
     * Type: double
     */
    public static final String VALUE_FORWARD_THRESHOLD = "forward_threshold";

    /**
     * The turn threshold to accept as within range of the angle (degrees)
     * Type: double
     */
    public static final String VALUE_TURN_THRESHOLD = "turn_threshold";

    /**
     * The path to follow
     * Type: com.thegongoliers.paths.Path
     */
    public static final String VALUE_PATH = "path";

    /**
     * Determines whether the drivetrain should follow the path
     * Setting this to true will cause the drivetrain to follow whatever path is set at that time until completed or the trigger is set to false
     * Type: bool
     */
    public static final String VALUE_TRIGGER = "trigger";


    /**
     * The name of the module
     */
    public static final String NAME = "Path Follower";

    private boolean lastTrigger;
    private SimplePath currentPath;
    private int currentStepIdx;

    /**
     * Default constructor
     * @param gyro the gyro used to determine the rotation of the drivetrain
     * @param strength the align strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will align the drivetrain
     */
    public PathFollowerModule(Gyro gyro, List<Encoder> encoders, double forwardStrength, double turnStrength){ // TODO: maybe set threshold instead of trigger
        super();
        values.put(VALUE_GYRO, gyro);
        values.put(VALUE_ENCODERS, encoders);
        values.put(VALUE_FORWARD_STRENGTH, forwardStrength);
        values.put(VALUE_TURN_STRENGTH, turnStrength);
        values.put(VALUE_TRIGGER, false);
        values.put(VALUE_FORWARD_THRESHOLD, 0.1);
        values.put(VALUE_TURN_THRESHOLD, 0.5);
        values.put(VALUE_PATH, null);

        currentPath = null;
        currentStepIdx = 0;
        lastTrigger = false;
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        boolean trigger = (boolean) getValue(VALUE_TRIGGER);

        if (!trigger){
            lastTrigger = false;
            return desiredSpeed;
        }

        if (!lastTrigger){ // When the trigger is first activated, load the path
            currentPath = (SimplePath) getValue(VALUE_PATH);
            currentStepIdx = 0;
            zeroSensors();
        }

        lastTrigger = trigger;

        if (currentPath == null){ // If the path is not set, unset the trigger and do nothing
            setValue(VALUE_TRIGGER, false);
            return desiredSpeed;
        }

        if (currentPath.getSteps().size() <= currentStepIdx){ // Finished following the path
            setValue(VALUE_TRIGGER, false);
            return desiredSpeed;
        }

        // There is a path to follow, so follow it


        // Get the current step
        PathStep step = currentPath.getSteps().get(currentStepIdx);

        DriveValue speed;

        // Determine the step type
        if (step.getType() == PathStepType.ROTATION){
            // Rotate to the target
            if (isRotated(step.getValue())){
                // Move on to next step
                currentStepIdx++;
                zeroSensors();
                return new DriveValue(0, 0);
            }
            speed = rotateTowards(step.getValue());
        } else {
            // Drive to the target
            if (atDistance(step.getValue())){
                // Move on to next step
                currentStepIdx++;
                zeroSensors();
                return new DriveValue(0, 0);
            }
            speed = driveTowards(step.getValue());
        }
        
        return speed;
    }

    private DriveValue rotateTowards(double angle){
        double turnStrength = (double) getValue(VALUE_TURN_STRENGTH);
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        double turnSpeed = GMath.clamp(turnStrength * (angle - gyro.getAngle()), -1, 1);
        return new DriveValue(0, turnSpeed);
    }

    private DriveValue driveTowards(double distance){
        double forwardStrength = (double) getValue(VALUE_FORWARD_STRENGTH);
        double speed = GMath.clamp(forwardStrength * (distance - getDistance()), -1, 1);
        return new DriveValue(speed, 0);
    }

    private boolean isRotated(double angle){
        double turnThreshold = (double) getValue(VALUE_TURN_THRESHOLD);
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        return Math.abs(gyro.getAngle() - angle) <= turnThreshold;  
    }

    private boolean atDistance(double distance){
        double forwardThreshold = (double) getValue(VALUE_FORWARD_THRESHOLD);
        double currentDistance = getDistance();
        return Math.abs(currentDistance - distance) <= forwardThreshold;  
    }

    private double getDistance(){
        List<Encoder> encoders = (List<Encoder>) getValue(VALUE_ENCODERS);
        double sum = 0;
        for (Encoder encoder : encoders) {
            sum += encoder.getDistance();
        }
        return sum / encoders.size();
    }

    private void zeroSensors(){
        List<Encoder> encoders = (List<Encoder>) getValue(VALUE_ENCODERS);
        for (Encoder encoder : encoders) {
            encoder.reset();
        }
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        gyro.reset();
    }

    @Override
    public String getName() {
        return NAME;
    }
}