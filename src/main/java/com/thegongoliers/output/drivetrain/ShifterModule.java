package com.thegongoliers.output.drivetrain;

import com.thegongoliers.output.gears.GearShifter;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which allows switching between gears.
 */
public class ShifterModule extends BaseDriveModule {

    /**
     * The gear shifter
     * Type: com.thegongoliers.output.gears.GearShifter
     */
    public static final String VALUE_SHIFTER = "shifter";

    /**
     * The trigger to shift to a higher gear
     * Type: edu.wpi.first.wpilibj.Trigger
     */
    public static final String VALUE_UPSHIFT_TRIGGER = "shift_up_trigger";

    /**
     * The trigger to shift to a lower gear
     * Type: edu.wpi.first.wpilibj.Trigger
     */
    public static final String VALUE_DOWNSHIFT_TRIGGER = "shift_down_trigger";

    /**
     * The name of the module
     */
    public static final String NAME = "Shifter";

    private boolean previousUpshift = false;
    private boolean previousDownshift = false;

   
    /**
     * Default constructor
     * @param shifter The shifter
     * @param downshiftTrigger The trigger to shift to a lower gear
     * @param upshiftTrigger The trigger to shift to a higher gear
     */
    public ShifterModule(GearShifter shifter, Trigger downshiftTrigger, Trigger upshiftTrigger){
        super();
        values.put(VALUE_SHIFTER, shifter);
        values.put(VALUE_DOWNSHIFT_TRIGGER, downshiftTrigger);
        values.put(VALUE_UPSHIFT_TRIGGER, upshiftTrigger);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        Trigger upshift = (Trigger) getValue(VALUE_UPSHIFT_TRIGGER);
        Trigger downshift = (Trigger) getValue(VALUE_DOWNSHIFT_TRIGGER);
        GearShifter shifter = (GearShifter) getValue(VALUE_SHIFTER);

        boolean upshiftPressed = upshift.get();
        boolean downshiftPressed = downshift.get();

        if (!previousUpshift && upshiftPressed){
            shifter.upshift();
        } else if (!previousDownshift && downshiftPressed){
            shifter.downshift();
        }

        previousUpshift = upshiftPressed;
        previousDownshift = downshiftPressed;

        return desiredSpeed;
    }

    @Override
    public String getName() {
        return NAME;
    }

}