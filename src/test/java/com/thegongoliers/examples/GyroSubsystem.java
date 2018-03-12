package com.thegongoliers.examples;

import com.thegongoliers.subsystems.RobotSubsystem;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroSubsystem extends RobotSubsystem {

    private Gyro gyro;

    private static GyroSubsystem instance;

    private GyroSubsystem(){
        gyro = new AnalogGyro(RobotMap.kGyroPort);
    }

    public static GyroSubsystem getInstance(){
        if(instance != null){
            instance = new GyroSubsystem();
        }
        return instance;
    }


    @Override
    public void publish() {
        SmartDashboard.putNumber("Gyro Angle: ", gyro.getAngle());
        SmartDashboard.putNumber("Gyro Rate: ", gyro.getRate());
    }

    @Override
    public void initialize() {
        gyro.calibrate();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getAngle(){
        return gyro.getAngle();
    }

    public void reset(){
        gyro.reset();
    }

}
