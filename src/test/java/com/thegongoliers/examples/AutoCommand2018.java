package com.thegongoliers.examples;

import com.thegongoliers.commands.GameSpecificMessageCommand;
import com.thegongoliers.input.gameMessages.GameSpecificMessage2018;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCommand2018 extends CommandGroup {

    public AutoCommand2018(){
        GameSpecificMessageCommand.GameSpecificMessageHandler handler = message ->
                Robot.side == message.getAllianceSwitch() && Robot.side != GameSpecificMessage2018.Location.UNKNOWN;

        addSequential(new GameSpecificMessageCommand(new RaiseCube(), new DoNothingCommand(), handler));
        addSequential(new DriveForwardCommand());
        addSequential(new GameSpecificMessageCommand(new DropCube(), new DoNothingCommand(), handler));
        addSequential(new StopCommand());
    }
}
