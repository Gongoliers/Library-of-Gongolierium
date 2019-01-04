package com.thegongoliers.examples;

import com.thegongoliers.subsystems.RobotSubsystem;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends RobotSubsystem {

    private DifferentialDrive differentialDrive;
    private SpeedController leftMotor, rightMotor;

    private static Drivetrain instance;

    private Drivetrain(){
        leftMotor = new VictorSP(RobotMap.kLeftMotor);
        rightMotor = new VictorSP(RobotMap.kRightMotor);
        differentialDrive = new DifferentialDrive(leftMotor, rightMotor);
    }

    public static Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }


    @Override
    public void publish() {
        SmartDashboard.putNumber("Drivetrain left speed", leftMotor.get());
        SmartDashboard.putNumber("Drivetrain right speed", rightMotor.get());
        SmartDashboard.putNumber("Drivetrain orientation", GyroSubsystem.getInstance().getAngle());
    }

    @Override
    public void initialize() {
        rightMotor.setInverted(true);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
