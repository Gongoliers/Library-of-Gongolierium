package com.thegongoliers.output.drivetrain;

import com.thegongoliers.output.gears.GearShifter;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which allows switching between gears.
 */
public class ShifterModule implements DriveModule {

    private Trigger mDownshiftTrigger, mUpshiftTrigger;
    private double mShiftStopTime;
    private GearShifter mShifter;

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
        setShifter(shifter);
        setDownshiftTrigger(downshiftTrigger);
        setUpshiftTrigger(upshiftTrigger);
        setShiftStopTime(shiftStopTime);
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
        boolean upshiftPressed = mUpshiftTrigger.get();
        boolean downshiftPressed = mDownshiftTrigger.get();

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
                if (timeInState >= mShiftStopTime){
                    mShifter.downshift();
                    timeInState = 0;
                    state = State.DO_NOTHING;
                } else {
                    speed = DriveSpeed.STOP;
                }
                break;
            case UPSHIFTING:
                timeInState += deltaTime;
                if (timeInState >= mShiftStopTime){
                    mShifter.upshift();
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

    public void setShifter(GearShifter shifter){
        if (shifter == null) throw new IllegalArgumentException("Shifter must be non-null");
        mShifter = shifter;
    }

    public void setUpshiftTrigger(Trigger upshiftTrigger){
        if (upshiftTrigger == null) throw new IllegalArgumentException("Upshift trigger must be non-null");
        mUpshiftTrigger = upshiftTrigger;
    }

    public void setDownshiftTrigger(Trigger downshiftTrigger){
        if (downshiftTrigger == null) throw new IllegalArgumentException("Downshift trigger must be non-null");
        mDownshiftTrigger = downshiftTrigger;
    }

    public void setShiftStopTime(double shiftStopTime){
        if (shiftStopTime < 0) throw new IllegalArgumentException("Shift stop time must be non-negative");
        mShiftStopTime = shiftStopTime;
    }

}