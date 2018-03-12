package com.thegongoliers.commands;

import com.thegongoliers.input.gameMessages.GameSpecificMessage2018;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class GameSpecificMessageCommand extends ConditionalCommand {

    private GameSpecificMessageHandler handler;

    public interface GameSpecificMessageHandler {
        boolean shouldRunTrueCommand(GameSpecificMessage2018 message);
    }

    public GameSpecificMessageCommand(Command onTrue, Command onFalse, GameSpecificMessageHandler handler) {
        super(onTrue, onFalse);
        this.handler = handler;
    }

    @Override
    protected boolean condition() {
        return handler != null &&
                handler.shouldRunTrueCommand(
                        new GameSpecificMessage2018(DriverStation.getInstance().getGameSpecificMessage()));
    }
}
