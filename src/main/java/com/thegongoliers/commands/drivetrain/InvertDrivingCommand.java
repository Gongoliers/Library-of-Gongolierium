package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.GongolieriumException;
import com.thegongoliers.output.drivetrain.InvertModule;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class InvertDrivingCommand extends InstantCommand {

    private final InvertModule mModule;
    private final boolean mShouldToggle;
    private final boolean mShouldInvert;

    public InvertDrivingCommand(Subsystem subsystem, ModularDrivetrain drivetrain, boolean inverted) {
        addRequirements(subsystem);
        mModule = getModule(drivetrain);
        mShouldInvert = inverted;
        mShouldToggle = false;
    }

    public InvertDrivingCommand(Subsystem subsystem, ModularDrivetrain drivetrain) {
        addRequirements(subsystem);
        mModule = getModule(drivetrain);
        mShouldInvert = false;
        mShouldToggle = true;
    }

    @Override
    public void initialize() {
        if (mShouldToggle) {
            mModule.setEnabled(!mModule.isEnabled());
            return;
        }

        mModule.setEnabled(mShouldInvert);
    }

    private InvertModule getModule(ModularDrivetrain drivetrain) {
        var module = drivetrain.getInstalledModule(InvertModule.class);
        if (module == null) {
            throw new GongolieriumException("The drivetrain does not have an invert module installed.");
        }
        return module;
    }

}
