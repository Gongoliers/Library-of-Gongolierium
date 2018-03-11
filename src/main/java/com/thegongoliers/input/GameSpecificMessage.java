package com.thegongoliers.input;

import edu.wpi.first.wpilibj.DriverStation;

public class GameSpecificMessage {

    public String getMessage(){
        return DriverStation.getInstance().getGameSpecificMessage();
    }

}
