package com.thegongoliers.commands;

import com.thegongoliers.input.gameMessages.GameSpecificMessage2018;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class GameSpecificMessageCommand extends ConditionalCommand {


    public interface GameSpecificMessageHandler {
        boolean shouldRunTrueCommand(GameSpecificMessage2018 message);
    }

    public GameSpecificMessageCommand(Command onTrue, Command onFalse, GameSpecificMessageHandler handler) {
        super(onTrue, onFalse, () -> {
            return handler != null && handler.shouldRunTrueCommand(
                new GameSpecificMessage2018(DriverStation.getInstance().getGameSpecificMessage()));
        });
    }
}
