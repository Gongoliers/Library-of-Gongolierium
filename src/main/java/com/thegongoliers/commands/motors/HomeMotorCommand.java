package com.thegongoliers.commands.motors;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.motors.LimiterMotorModule;
import com.thegongoliers.output.motors.ModularMotor;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class HomeMotorCommand extends CommandBase {

    private final LimiterMotorModule mModule;
    private final MotorController mMotor;
    private final double mSpeed;
    private final Runnable mZeroSensors;

    public HomeMotorCommand(Subsystem subsystem, ModularMotor motor, double speed, Runnable zeroSensors) {
        addRequirements(subsystem);
        mModule = motor.getInstalledModule(LimiterMotorModule.class);
        if (mModule == null) {
            throw new GongolieriumException("The motor does not have a limiter module installed.");
        }
        mMotor = motor;
        mSpeed = speed;
        mZeroSensors = zeroSensors;
    }

    @Override
    public void execute() {
        mMotor.set(mSpeed);
    }

    @Override
    public boolean isFinished() {
        return (mSpeed > 0 && mModule.isAtPositiveLimit()) ||
                (mSpeed < 0 && mModule.isAtNegativeLimit());
    }

    @Override
    public void end(boolean interrupted) {
        mMotor.stopMotor();
        if (!interrupted) {
            mZeroSensors.run();
        }
    }
}
