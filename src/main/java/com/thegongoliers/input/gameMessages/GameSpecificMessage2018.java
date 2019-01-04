package com.thegongoliers.input.gameMessages;

public class GameSpecificMessage2018  {

    public enum Location {
        LEFT, RIGHT, UNKNOWN
    }

    private String message;

    public GameSpecificMessage2018(String message) {
        this.message = message;
        if(message == null){
            this.message = "";
        }
    }

    public Location getAllianceSwitch(){
        if(message.length() > 0){
            char pos = message.charAt(0);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

    public Location getOpposingAllianceSwitch(){
        if(message.length() > 2){
            char pos = message.charAt(2);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

    public Location getScale(){
        if(message.length() > 1){
            char pos = message.charAt(1);
            if(Character.toLowerCase(pos) == 'r'){
                return Location.RIGHT;
            } else {
                return Location.LEFT;
            }
        }
        return Location.UNKNOWN;
    }

}
