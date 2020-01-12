package com.thegongoliers.math.exceptions;

public class OutOfBoundsException extends RuntimeException {

    private static final long serialVersionUID = 4018838947015828655L;

    public OutOfBoundsException() {
        super("Value out of bounds.");
    }

}
