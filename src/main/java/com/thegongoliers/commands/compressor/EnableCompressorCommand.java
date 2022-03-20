package com.thegongoliers.commands.compressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class EnableCompressorCommand extends InstantCommand {
    public EnableCompressorCommand(Subsystem subsystem, Compressor compressor){
        super(compressor::enableDigital, subsystem);
    }
}
