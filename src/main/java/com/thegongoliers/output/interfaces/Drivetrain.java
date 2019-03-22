package com.thegongoliers.output.interfaces;

public interface Drivetrain extends Stoppable {

    /**
     * Drive in arcade mode.
     * @param speed A percent speed for forward/backward motion between -1 and 1, inclusive. 1 is forward and -1 is reverse.
     * @param turn A percent speed for turning between -1 and 1, inclusive. 1 is clockwise and -1 is counter clockwise.
     */
    void arcade(double speed, double turn);

    /**
     * Drive in tank mode.
     * @param leftSpeed A percent speed for the left side between -1 and 1, inclusive. 1 is forward and -1 is reverse.
     * @param rightSpee A percent speed for the right side between -1 and 1, inclusive. 1 is forward and -1 is reverse.
     */
    void tank(double leftSpeed, double rightSpeed);
}