package com.thegongoliers.commands.compressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.BooleanSupplier;

public class ControlCompressorCommand extends CommandBase {

    private final Compressor mCompressor;
    private final BooleanSupplier mShouldEnable;

    public ControlCompressorCommand(Subsystem subsystem, Compressor compressor, BooleanSupplier shouldEnable) {
        addRequirements(subsystem);
        mCompressor = compressor;
        mShouldEnable = shouldEnable;
    }


    @Override
    public void execute() {
        var shouldEnable = mShouldEnable.getAsBoolean();
        var isEnabled = mCompressor.enabled();
        if (shouldEnable && !isEnabled) {
            mCompressor.enableDigital();
        } else if (!shouldEnable && isEnabled) {
            mCompressor.disable();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
