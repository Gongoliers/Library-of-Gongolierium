package com.thegongoliers;

public class GongolieriumException extends RuntimeException {

    private static final long serialVersionUID = 3963281343370601842L;

    /**
     * Default constructor
     * 
     * @param message the error message
     */
    public GongolieriumException(String message){
        super(message);
    }
}
