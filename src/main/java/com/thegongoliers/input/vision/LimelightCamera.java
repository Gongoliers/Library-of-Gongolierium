package com.thegongoliers.input.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightCamera implements TargetingCamera {

    private static final String TABLE_NAME = "limelight";
    private static final String HAS_TARGET_KEY = "tv";
    private static final String HORIZONTAL_OFFSET_KEY = "tx";
    private static final String VERTICAL_OFFSET_KEY = "ty";
    private static final String TARGET_AREA_KEY = "ta";
    private static final String LED_MODE_KEY = "ledMode";
    private static final String CAMERA_MODE_KEY = "camMode";

    private NetworkTable mNetworkTable;

    public LimelightCamera(){
        mNetworkTable = NetworkTableInstance.getDefault().getTable(TABLE_NAME);
    }

    @Override
    public boolean hasTarget() {
        return mNetworkTable.getEntry(HAS_TARGET_KEY).getDouble(0.0) > 0;
    }

    @Override
    public double getHorizontalOffset() {
        return mNetworkTable.getEntry(HORIZONTAL_OFFSET_KEY).getDouble(0.0);
    }

    @Override
    public double getVerticalOffset() {
        return mNetworkTable.getEntry(VERTICAL_OFFSET_KEY).getDouble(0.0);
    }

    @Override
    public double getTargetArea() {
        return mNetworkTable.getEntry(TARGET_AREA_KEY).getDouble(0.0);
    }


    public void setLEDMode(LEDMode mode){
        mNetworkTable.getEntry(LED_MODE_KEY).setNumber(mode.modeNumber);
    }

    public void switchToDriverMode(){
        mNetworkTable.getEntry(CAMERA_MODE_KEY).setNumber(1);
    }

    public void switchToTargetingMode(){
        mNetworkTable.getEntry(CAMERA_MODE_KEY).setNumber(0);
    }

    public static enum LEDMode {
        UseDefault(0),
        Off(1),
        Blink(2),
        On(3);

        public final int modeNumber;

        LEDMode(int modeNumber){
            this.modeNumber = modeNumber;
        }
    }

}