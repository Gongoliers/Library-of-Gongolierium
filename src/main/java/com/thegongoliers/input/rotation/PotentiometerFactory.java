package com.thegongoliers.input.rotation;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * Creates commonly used potentiometers
 */
public class PotentiometerFactory {

    /**
     * The type of potentiometer
     */
    public enum PotentiometerType {
        AM_2619(3600);

        private double scale;

        PotentiometerType(double scale){
            this.scale = scale;
        }

        /**
         * @return the scale of the potentiometer
         */
        public double getScale(){
            return scale;
        }
    }

    /**
     * Get a potentiometer
     * @param port the port of the potentiometer
     * @param type the type of potentiometer
     * @param zeroPoint The zero point of the potentiometer. If you expect a position to be 0, but it is reading X degrees, put X here. (ex. actual 1000 to 1100 degrees, expected 0 to 100 degrees. Put 1000 here.)
     * @return a potentiometer
     */
    public static Potentiometer getPotentiometer(int port, PotentiometerType type, double zeroPoint){
        if (type == null || port < 0){
            return null;
        }
        return new AnalogPotentiometer(port, type.getScale(), -zeroPoint);
    }

}
