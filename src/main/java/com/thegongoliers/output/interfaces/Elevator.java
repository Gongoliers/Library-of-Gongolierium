package com.thegongoliers.output.interfaces;

/**
 * Represents an elevator mechanism which moves up and down.
 */
public interface Elevator extends Stoppable {

    /**
     * @param height the height of the elevator carrage in units.
     */
    void setHeight(double height);

    /**
     * @return the height of the eleveator carrage in units.
     */
    double getHeight();

}