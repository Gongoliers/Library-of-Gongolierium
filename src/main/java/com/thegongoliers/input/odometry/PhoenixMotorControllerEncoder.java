package com.thegongoliers.input.odometry;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class PhoenixMotorControllerEncoder extends BaseEncoderSensor {

    private BaseMotorController mMotor;
    private FeedbackDevice mDevice;

    public PhoenixMotorControllerEncoder(BaseMotorController motor, FeedbackDevice device) {
        super(motor::getSelectedSensorPosition, motor::getSelectedSensorVelocity);
        mMotor = motor;
        mDevice = device;
        reselectFeedbackDevice();
    }

    public void reselectFeedbackDevice(){
        mMotor.configSelectedFeedbackSensor(mDevice);
    }

}