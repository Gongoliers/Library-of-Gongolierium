package com.thegongoliers.output.drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class AnchorModule extends BaseDriveModule {

    /**
     * The left encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_LEFT_ENCODER = "left_encoder";

    /**
     * The right encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_RIGHT_ENCODER = "right_encoder";

    /**
     * The fortify strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The trigger which will lock the drivetrain in place
     * Type: edu.wpi.first.wpilibj.buttons.Trigger
     */
    public static final String VALUE_TRIGGER = "trigger";

    /**
     * The name of the module
     */
    public static final String NAME = "Anchor";

    private double lastLeftDistance, lastRightDistance;
    private boolean lastTrigger;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will lock the drivetrain in place
     */
    public AnchorModule(Encoder leftEncoder, Encoder rightEncoder, double strength, Trigger trigger){
        super();
        values.put(VALUE_LEFT_ENCODER, leftEncoder);
        values.put(VALUE_RIGHT_ENCODER, rightEncoder);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_TRIGGER, trigger);

        lastLeftDistance = leftEncoder.getDistance();
        lastRightDistance = rightEncoder.getDistance();
        lastTrigger = false;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Encoder leftEncoder = (Encoder) getValue(VALUE_LEFT_ENCODER);
        Encoder rightEncoder = (Encoder) getValue(VALUE_RIGHT_ENCODER);
        Trigger trigger = (Trigger) getValue(VALUE_TRIGGER);
        boolean currentTrigger = trigger.get();

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();

        if (!lastTrigger && currentTrigger){
            lastLeftDistance = leftEncoder.getDistance();
            lastRightDistance = rightEncoder.getDistance();
        }

        if (currentTrigger){
            left = strength * (lastLeftDistance - leftEncoder.getDistance());
            right = strength * (lastRightDistance - rightEncoder.getDistance());;
        }

        lastTrigger = currentTrigger;

        return new DriveSpeed(left, right);
    }

    @Override
    public String getName() {
        return NAME;
    }
}