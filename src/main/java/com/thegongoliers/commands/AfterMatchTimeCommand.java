package com.thegongoliers.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class AfterMatchTimeCommand extends ConditionalCommand {
    public AfterMatchTimeCommand(Command command, double secondsRemaining) {
        super(command, new DoNothingCommand(), () -> Timer.getMatchTime() <= secondsRemaining);
    }
}
