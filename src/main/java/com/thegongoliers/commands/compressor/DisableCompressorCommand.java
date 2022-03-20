package com.thegongoliers.commands.compressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DisableCompressorCommand extends InstantCommand {
    public DisableCompressorCommand(Subsystem subsystem, Compressor compressor){
        super(compressor::disable, subsystem);
    }
}
