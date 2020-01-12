package com.thegongoliers.input.gameMessages;

import edu.wpi.first.wpilibj.DriverStation;

public class GameSpecificMessage2020 {

    public enum ColorAssignment {
        Red,
        Green,
        Blue,
        Yellow,
        Unknown
    }

    public ColorAssignment getColorAssignment(){
        return parseMessage(DriverStation.getInstance().getGameSpecificMessage());
    }

    private ColorAssignment parseMessage(String message){
        if (!isValidMessage(message)) return ColorAssignment.Unknown;
        char code = message.toUpperCase().charAt(0);
        switch(code){
            case 'B': return ColorAssignment.Blue;
            case 'G': return ColorAssignment.Green;
            case 'R': return ColorAssignment.Red;
            case 'Y': return ColorAssignment.Yellow;
        }
        return ColorAssignment.Unknown;
    }

    private boolean isValidMessage(String message) {
        return message != null && message.length() != 0;
    }

}