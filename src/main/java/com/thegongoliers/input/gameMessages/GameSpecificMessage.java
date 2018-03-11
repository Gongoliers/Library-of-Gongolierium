package com.thegongoliers.input.gameMessages;

import edu.wpi.first.wpilibj.DriverStation;

public class GameSpecificMessage {

    public String getMessage(){
        return DriverStation.getInstance().getGameSpecificMessage();
    }

}
