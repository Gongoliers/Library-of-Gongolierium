package com.thegongoliers.input.orientation;

import com.kylecorry.geometry.Orientation;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class OrientationSensor {

    private final Gyro gyro;
    private final TiltSensor tiltSensor;


    public OrientationSensor(Accelerometer accelerometer, Gyro gyro) {
        this.gyro = gyro;
        tiltSensor = new TiltSensor(accelerometer);
    }

    public Orientation getOrientation() {
        return new Orientation(tiltSensor.getRoll(), tiltSensor.getPitch(), gyro.getAngle());
    }
}
