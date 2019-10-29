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
     * The amount of time in seconds to stop the motors before shifting gears
     * Type: double
     */
    public static final String VALUE_SHIFT_STOP_TIME = "stop_time";

    /**
     * The name of the module
     */
    public static final String NAME = "Shifter";

    private boolean previousUpshift = false;
    private boolean previousDownshift = false;


    private enum State {
        DOWNSHIFTING,
        UPSHIFTING,
        DO_NOTHING
    }

    private State state;
    private double timeInState;

   
    /**
     * Default constructor
     * @param shifter The shifter
     * @param downshiftTrigger The trigger to shift to a lower gear
     * @param upshiftTrigger The trigger to shift to a higher gear
     * @param shiftStopTime The amount of time in seconds to stop the motors before shifting gears
     */
    public ShifterModule(GearShifter shifter, Trigger downshiftTrigger, Trigger upshiftTrigger, double shiftStopTime){
        super();
        values.put(VALUE_SHIFTER, shifter);
        values.put(VALUE_DOWNSHIFT_TRIGGER, downshiftTrigger);
        values.put(VALUE_UPSHIFT_TRIGGER, upshiftTrigger);
        values.put(VALUE_SHIFT_STOP_TIME, shiftStopTime);
        state = State.DO_NOTHING;
        timeInState = 0;
    }

    /**
     * Default constructor
     * @param shifter The shifter
     * @param downshiftTrigger The trigger to shift to a lower gear
     * @param upshiftTrigger The trigger to shift to a higher gear
     */
    public ShifterModule(GearShifter shifter, Trigger downshiftTrigger, Trigger upshiftTrigger){
        this(shifter, downshiftTrigger, upshiftTrigger, 0);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        Trigger upshift = (Trigger) getValue(VALUE_UPSHIFT_TRIGGER);
        Trigger downshift = (Trigger) getValue(VALUE_DOWNSHIFT_TRIGGER);
        GearShifter shifter = (GearShifter) getValue(VALUE_SHIFTER);
        double stopTime = (double) getValue(VALUE_SHIFT_STOP_TIME);

        boolean upshiftPressed = upshift.get();
        boolean downshiftPressed = downshift.get();

        DriveSpeed speed = desiredSpeed;

        if (state != State.UPSHIFTING && !previousUpshift && upshiftPressed){
            state = State.UPSHIFTING;
        } else if (state != State.DOWNSHIFTING && !previousDownshift && downshiftPressed){
            state = State.DOWNSHIFTING;
        }

        previousUpshift = upshiftPressed;
        previousDownshift = downshiftPressed;

        switch (state){
            case DOWNSHIFTING:
                timeInState += deltaTime;
                if (timeInState >= stopTime){
                    shifter.downshift();
                    timeInState = 0;
                    state = State.DO_NOTHING;
                } else {
                    speed = DriveSpeed.STOP;
                }
                break;
            case UPSHIFTING:
                timeInState += deltaTime;
                if (timeInState >= stopTime){
                    shifter.upshift();
                    timeInState = 0;
                    state = State.DO_NOTHING;
                } else {
                    speed = DriveSpeed.STOP;
                }
                break;
            case DO_NOTHING:
                timeInState = 0;
                break;
        }

        return speed;
    }

    @Override
    public String getName() {
        return NAME;
    }

}