package com.thegongoliers.input.orientation;

import com.kylecorry.geometry.Orientation;
import com.thegongoliers.mockHardware.input.MockAccelerometer;
import com.thegongoliers.mockHardware.input.MockGyro;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class OrientationSensor {

    private Gyro gyro;
    private TiltSensor tiltSensor;


    public OrientationSensor(Accelerometer accelerometer, Gyro gyro) {
        this.gyro = gyro;
        tiltSensor = new TiltSensor(accelerometer);
    }

    public Orientation getOrientation() {
        return new Orientation(tiltSensor.getRoll(), tiltSensor.getPitch(), gyro.getAngle());
    }
}
