package com.thegongoliers.output.drivetrain;

import com.thegongoliers.output.gears.GearShifter;

/**
 * A drivetrain module which allows switching between gears.
 */
public class ShifterModule implements DriveModule {

    private double mShiftStopTime;
    private GearShifter mShifter;

    private enum ShiftDirection {
        Down,
        Up,
        Nothing
    }

    private ShiftDirection state;
    private ShiftDirection mRequestedState;

    private double timeInState;

   
    /**
     * Default constructor
     * @param shifter The shifter
     * @param shiftStopTime The amount of time in seconds to stop the motors before shifting gears
     */
    public ShifterModule(GearShifter shifter, double shiftStopTime){
        super();
        setShifter(shifter);
        setShiftStopTime(shiftStopTime);
        state = ShiftDirection.Nothing;
        timeInState = 0;
        mRequestedState = ShiftDirection.Nothing;
    }

    public ShifterModule(GearShifter shifter){
        this(shifter, 0);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (shouldShift(ShiftDirection.Up)){
            startShifting(ShiftDirection.Up);
        } else if (shouldShift(ShiftDirection.Down)){
            startShifting(ShiftDirection.Down);
        }

        if (!isShifting()){
            return desiredSpeed;
        }

        return shift(desiredSpeed, deltaTime);
    }

    private DriveSpeed shift(DriveSpeed desiredSpeed, double deltaTime) {
        timeInState += deltaTime;

        if (timeInState < mShiftStopTime) return DriveSpeed.STOP;

        if (state == ShiftDirection.Down){
            mShifter.downshift();
        } else {
            mShifter.upshift();
        }
        timeInState = 0;
        state = ShiftDirection.Nothing;

        return desiredSpeed;
    }

    private boolean isShifting() {
        return state != ShiftDirection.Nothing;
    }

    private void startShifting(ShiftDirection direction) {
        this.state = direction;
        mRequestedState = ShiftDirection.Nothing;
        timeInState = 0;
    }

    private boolean shouldShift(ShiftDirection direction) {
        return state != direction && mRequestedState == direction;
    }

    public void setShifter(GearShifter shifter){
        if (shifter == null) throw new IllegalArgumentException("Shifter must be non-null");
        mShifter = shifter;
    }

    public void upshift(){
        mRequestedState = ShiftDirection.Up;

    }

    public void downshift(){
        mRequestedState = ShiftDirection.Down;
    }

    public void setShiftStopTime(double shiftStopTime){
        if (shiftStopTime < 0) throw new IllegalArgumentException("Shift stop time must be non-negative");
        mShiftStopTime = shiftStopTime;
    }

}