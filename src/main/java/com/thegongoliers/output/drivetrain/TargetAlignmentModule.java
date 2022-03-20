package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.vision.TargetingCamera;
import com.thegongoliers.math.GMath;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.utils.Resettable;

/**
 * A drivetrain module which align to vision targets
 */
public class TargetAlignmentModule implements DriveModule, Resettable {
    private static final double DEFAULT_SEEK_SPEED = 0.3;

    private TargetingCamera mCamera;
    private boolean mShouldSeek;
    private MotionController mAimController;
    private MotionController mRangeController;
    private double mSeekSpeed;

    private double mDesiredHorizontalOffset;
    private double mDesiredTargetArea;

    private boolean mIsEnabled;

    public TargetAlignmentModule(TargetingCamera camera, MotionController aimController, MotionController rangeController, boolean shouldSeek){
        mCamera = camera;
        setShouldSeek(shouldSeek);
        setAimController(aimController);
        setRangeController(rangeController);
        setSeekSpeed(DEFAULT_SEEK_SPEED);
        
        if (cantAimOrRange()){
            throw new IllegalArgumentException("Range strength and aim strength can't both be 0");
        }
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (!mIsEnabled) return desiredSpeed;
        if (cantFindTarget()) return desiredSpeed;

        if (!mCamera.hasTarget()){
            return seek();
        }

        var alignmentSpeed = align(deltaTime);

        if (isAligned()){
            stopAligning();
            return desiredSpeed;
        }
        
        return alignmentSpeed;
    }

    @Override
    public boolean overridesUser() {
        return isAligning();
    }

    public void align(double desiredHorizontalOffset, double desiredTargetArea){
        mDesiredTargetArea = desiredTargetArea;
        mDesiredHorizontalOffset = desiredHorizontalOffset;
        mIsEnabled = true;
        resetControllers();
    }

    public void stopAligning(){
        mIsEnabled = false;
        resetControllers();
    }

    public boolean isAligning(){
        return mIsEnabled;
    }

    public void setShouldSeek(boolean shouldSeek){
        mShouldSeek = shouldSeek;
    }

    public void setSeekSpeed(double speed){
        if (speed < -1 || speed > 1) throw new IllegalArgumentException("Speed must be between -1 and 1");
        mSeekSpeed = speed;
    }

    public void setRangeController(MotionController rangeController){
        mRangeController = rangeController;
    }

    public void setAimController(MotionController aimController){
        mAimController = aimController;
    }

    private boolean cantAimOrRange() {
        return shouldOnlyAlignAim() && shouldOnlyAlignRange();
    }

    private boolean cantFindTarget() {
        return !mCamera.hasTarget() && !mShouldSeek;
    }

    private DriveSpeed seek(){
        return DriveSpeed.fromArcade(0, mSeekSpeed);
    }

    private boolean isAligned(){
        if (shouldOnlyAlignRange()){
            return isRangeAligned();
        } else if (shouldOnlyAlignAim()){
            return isAimAligned();
        }
        return isAimAligned() && isRangeAligned();
    }

    private boolean isAimAligned() {
        if (mAimController == null) return true;
        return mAimController.atSetpoint();
    }

    private boolean isRangeAligned(){
        if (mRangeController == null) return true;
        return mRangeController.atSetpoint();
    }

    private DriveSpeed align(double delta){
        double aim = 0.0;
        double range = 0.0;

        if (mAimController != null){
            mAimController.setSetpoint(mDesiredHorizontalOffset);
            aim = mAimController.calculate(mCamera.getHorizontalOffset(), delta);
        }

        if (mRangeController != null){
            mRangeController.setSetpoint(mDesiredTargetArea);
            range = mRangeController.calculate(mCamera.getTargetArea(), delta);
        }

        return DriveSpeed.fromArcade(range, aim);
    }

    private boolean shouldOnlyAlignRange() {
        return mAimController == null;
    }

    private boolean shouldOnlyAlignAim() {
        return mRangeController == null;
    }

    @Override
    public void reset() {
        stopAligning();
    }

    private void resetControllers(){
        if (mRangeController != null){
            mRangeController.reset();
        }

        if (mAimController != null){
            mAimController.reset();
        }
    }
}