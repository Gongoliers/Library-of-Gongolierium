package com.thegongoliers.output.drivetrain;

import java.util.List;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.vision.TargetingCamera;
import com.thegongoliers.output.interfaces.Drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class GDrivetrain implements Drivetrain {

    private DifferentialDrive drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private EncoderSensor _leftEncoder;
    private EncoderSensor _rightEncoder;
    private Gyro _gyro;
    private TargetingCamera _camera;
    
    public GDrivetrain(MotorController left, MotorController right, EncoderSensor leftEncoder, EncoderSensor rightEncoder, Gyro gyro, TargetingCamera camera){
        drivetrain = new DifferentialDrive(left, right);
        _leftEncoder = leftEncoder;
        _rightEncoder = rightEncoder;
        _gyro = gyro;
        _camera = camera;

        var aimPID = new PID(0.12, 0.05, 0.005);
        var rangePID = new PID(0, 0, 0);
        var shouldSeek = false;
        var targetModule = new TargetAlignmentModule(camera, aimPID, rangePID, shouldSeek);


        var pathModule = new PathFollowerModule(gyro, new AverageEncoderSensor(leftEncoder, rightEncoder), 0.5, 0.02);
        pathModule.setForwardTolerance(0.5);
        pathModule.setTurnTolerance(1);

        var stabilityModule = new StabilityModule(gyro, 0.05, 0.25);
        stabilityModule.setTurnThreshold(0.075);

        modularDrivetrain = ModularDrivetrain.from(drivetrain);
        modularDrivetrain.setModules(
            stabilityModule,
            pathModule,
            targetModule,
            new VoltageControlModule(10.5),
            new PowerEfficiencyModule(0.25, 0.2)
        );
    }

    public TargetingCamera getCamera(){
        return _camera;
    }

    public double getSpeed(){
        return (_leftEncoder.getVelocity() + _rightEncoder.getVelocity()) / 2;
    }

    public double getDistance(){
        return (_leftEncoder.getDistance() + _rightEncoder.getDistance()) / 2;
    }

    public double getHeading(){
        return _gyro.getAngle();
    }

    public <T> T getModule(Class<T> module){
        return modularDrivetrain.getInstalledModule(module);
    }

    public void setMaxVoltage(double voltage){
        var module = modularDrivetrain.getInstalledModule(VoltageControlModule.class);
        if (module != null){
            module.setMaxVoltage(voltage);
        }
    }

    public void setRampTime(double secondsToReachFullSpeed){
        var module = modularDrivetrain.getInstalledModule(PowerEfficiencyModule.class);
        if (module != null){
            module.setRampingTime(secondsToReachFullSpeed);
        }
    }

    public void setTargetingStrength(PID aim, PID range){
        var module = modularDrivetrain.getInstalledModule(TargetAlignmentModule.class);
        if (module != null){
            module.setAimPID(aim);
            module.setRangePID(range);
        }
    }

    public void setPathFollowingStrength(PID forward, PID turn){
        var module = modularDrivetrain.getInstalledModule(PathFollowerModule.class);
        if (module != null){
            module.setForwardPID(forward);
            module.setTurnPID(turn);
        }
    }

    public void setPathFollowingThreshold(double forward, double turnDegrees){
        var module = modularDrivetrain.getInstalledModule(PathFollowerModule.class);
        if (module != null){
            module.setForwardTolerance(forward);
            module.setTurnTolerance(turnDegrees);
        }
    }

    public void setStabilityStrength(double strength){
        var module = modularDrivetrain.getInstalledModule(StabilityModule.class);
        if (module != null){
            module.setStrength(strength);
        }
    }

    public void setStabilitySettlingTime(double settlingTime){
        var module = modularDrivetrain.getInstalledModule(StabilityModule.class);
        if (module != null){
            module.setSettlingTime(settlingTime);
        }
    }

    public ModularDrivetrain getDrivetrain(){
        return modularDrivetrain;
    }

    @Override
    public void tank(double left, double right){
        modularDrivetrain.tank(left, right);
    }

    @Override
    public void arcade(double forward, double turn){
        modularDrivetrain.arcade(forward, turn);
    }

    @Override
    public void stop(){
        modularDrivetrain.stop();
    }

}
