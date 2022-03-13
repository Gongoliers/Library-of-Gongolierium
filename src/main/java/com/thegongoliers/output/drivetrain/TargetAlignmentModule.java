package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.vision.TargetingCamera;
import com.thegongoliers.math.GMath;
import com.thegongoliers.utils.Resettable;

/**
 * A drivetrain module which align to vision targets
 */
public class TargetAlignmentModule implements DriveModule, Resettable {

    private static final double DEFAULT_RANGE_THRESHOLD = 0.1;
    private static final double DEFAULT_AIM_THRESHOLD = 0.1;
    private static final double DEFAULT_SEEK_SPEED = 0.3;

    private TargetingCamera mCamera;
    private boolean mShouldSeek;
    private PID mAimPID;
    private PID mRangePID;
    private double mSeekSpeed;

    private double mDesiredHorizontalOffset;
    private double mDesiredTargetArea;

    private boolean mIsEnabled;

    public TargetAlignmentModule(TargetingCamera camera, double aimStrength, double rangeStrength, boolean shouldSeek){
        this(camera, new PID(aimStrength, 0, 0), new PID(rangeStrength, 0, 0), shouldSeek);
        setRangeThreshold(DEFAULT_RANGE_THRESHOLD);
        setAimThreshold(DEFAULT_AIM_THRESHOLD);
    }

    public TargetAlignmentModule(TargetingCamera camera, PID aimPID, PID rangePID, boolean shouldSeek){
        mCamera = camera;
        setShouldSeek(shouldSeek);
        setAimPID(aimPID);
        setRangePID(rangePID);
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

        var alignmentSpeed = align();

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
        mAimPID.reset();
        mRangePID.reset();
    }

    public void stopAligning(){
        mIsEnabled = false;
        mAimPID.reset();
        mRangePID.reset();
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
        mRangePID.setPositionTolerance(threshold);
    }

    public void setAimThreshold(double threshold){
        if (threshold < 0) throw new IllegalArgumentException("Threshold must be non-negative");
        mAimPID.setPositionTolerance(threshold);
    }

    public void setRangePID(PID rangePID){
        if (rangePID == null) throw new IllegalArgumentException("PID must be non-null");
        mRangePID = rangePID;
    }

    public void setAimPID(PID aimPID){
        if (aimPID == null) throw new IllegalArgumentException("PID must be non-null");
        mAimPID = aimPID;
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
        return mAimPID.atSetpoint();
    }

    private boolean isRangeAligned(){
        return mRangePID.atSetpoint();
    }

    private DriveSpeed align(){
        var aim = mAimPID.calculate(mCamera.getHorizontalOffset(), mDesiredHorizontalOffset);
        var range = mRangePID.calculate(mCamera.getTargetArea(), mDesiredTargetArea);

        return DriveSpeed.fromArcade(range, aim);
    }

    private boolean shouldOnlyAlignRange() {
        return GMath.approximately(mAimPID.getP(), 0) && 
            GMath.approximately(mAimPID.getI(), 0) && 
            GMath.approximately(mAimPID.getD(), 0);
    }

    private boolean shouldOnlyAlignAim() {
        return GMath.approximately(mRangePID.getP(), 0) && 
            GMath.approximately(mRangePID.getI(), 0) && 
            GMath.approximately(mRangePID.getD(), 0);
    }

    @Override
    public void reset() {
        stopAligning();
    }
}