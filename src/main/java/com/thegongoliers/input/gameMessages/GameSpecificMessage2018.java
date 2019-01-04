package com.thegongoliers.input.gameMessages;

public class GameSpecificMessage2018  {

    /**
     * The location/side of the field based on the alliance's point of view.
     */
    public enum Location {
        LEFT, RIGHT, UNKNOWN
    }

    private String message;

    /**
     * Create a game specific message for the 2018 game.
     * @param message The message delivered from the driver station (ex. "rlr")
     */
    public GameSpecificMessage2018(String message) {
        this.message = message;
        if(message == null){
            this.message = "";
        }
    }

    /**
     * Get the location of the alliance switch.
     * @return The location of the alliance side switch.
     */
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

    /**
     * Get the location of the opposing alliance switch.
     * @return The location of the opposing alliance switch.
     */
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

    /**
     * Get the location of the scale.
     * @return The location of the scale.
     */
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
