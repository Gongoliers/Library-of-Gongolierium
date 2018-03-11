package com.thegongoliers.input;

import edu.wpi.first.wpilibj.DriverStation;

public class GameSpecificMessage2018 extends GameSpecificMessage {

    enum Location {
        LEFT, RIGHT, UNKNOWN
    }

    public Location getAllianceSwitch(){
        String gameMessage = getMessage();
        if(getMessage().length() > 0){
            char pos = gameMessage.charAt(0);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

    public Location getOpposingAllianceSwitch(){
        String gameMessage = getMessage();
        if(getMessage().length() > 2){
            char pos = gameMessage.charAt(2);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

    public Location getScale(){
        String gameMessage = getMessage();
        if(getMessage().length() > 1){
            char pos = gameMessage.charAt(1);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

}
