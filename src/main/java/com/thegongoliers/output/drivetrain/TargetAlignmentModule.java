package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.vision.TargetingCamera;
import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which align to vision targets
 */
public class TargetAlignmentModule implements DriveModule {

    private static final double DEFAULT_RANGE_THRESHOLD = 0.1;
    private static final double DEFAULT_AIM_THRESHOLD = 0.1;
    private static final double DEFAULT_SEEK_SPEED = 0.3;

    private TargetingCamera mCamera;
    private boolean mShouldSeek;
    private double mAimStrength;
    private double mRangeStrength;
    private double mSeekSpeed;
    private double mAimThreshold;
    private double mRangeThreshold;

    private double mDesiredHorizontalOffset;
    private double mDesiredTargetArea;

    private boolean mIsEnabled;

    public TargetAlignmentModule(TargetingCamera camera, double aimStrength, double rangeStrength, boolean shouldSeek){
        mCamera = camera;
        setShouldSeek(shouldSeek);
        setAimStrength(aimStrength);
        setRangeStrength(rangeStrength);
        setAimThreshold(DEFAULT_AIM_THRESHOLD);
        setRangeThreshold(DEFAULT_RANGE_THRESHOLD);
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

        if (isAligned()){
            stopAligning();
            return desiredSpeed;
        }
        
        return align();
    }

    @Override
    public boolean overridesUser() {
        return isAligning();
    }

    public void align(double desiredHorizontalOffset, double desiredTargetArea){
        mDesiredTargetArea = desiredTargetArea;
        mDesiredHorizontalOffset = desiredHorizontalOffset;
        mIsEnabled = true;
    }

    public void stopAligning(){
        mIsEnabled = false;
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

    public void setRangeThreshold(double threshold){
        if (threshold < 0) throw new IllegalArgumentException("Threshold must be non-negative");
        mRangeThreshold = threshold;
    }

    public void setAimThreshold(double threshold){
        if (threshold < 0) throw new IllegalArgumentException("Threshold must be non-negative");
        mAimThreshold = threshold;
    }

    public void setRangeStrength(double strength){
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mRangeStrength = strength;
    }

    public void setAimStrength(double strength){
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mAimStrength = strength;
    }

    private boolean cantAimOrRange() {
        return GMath.approximately(mRangeStrength, 0) && GMath.approximately(mAimStrength, 0);
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
        var aimError = mDesiredHorizontalOffset - mCamera.getHorizontalOffset();
        return Math.abs(aimError) <= mAimThreshold;
    }

    private boolean isRangeAligned(){
        var rangeError = mDesiredTargetArea - mCamera.getTargetArea();
        return Math.abs(rangeError) <= mRangeThreshold;
    }

    private DriveSpeed align(){
        var aimError = mDesiredHorizontalOffset - mCamera.getHorizontalOffset();
        var rangeError = mDesiredTargetArea - mCamera.getTargetArea();
        return DriveSpeed.fromArcade(rangeError * mRangeStrength, aimError * mAimStrength);
    }

    private boolean shouldOnlyAlignRange() {
        return GMath.approximately(mAimStrength, 0);
    }

    private boolean shouldOnlyAlignAim() {
        return GMath.approximately(mRangeStrength, 0);
    }
}