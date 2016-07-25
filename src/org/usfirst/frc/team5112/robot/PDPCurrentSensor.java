package frc.team5112.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Created by Kyle on 7/25/2016.
 */
public class PDPCurrentSensor {

    private int port;
    private PowerDistributionPanel pdp;

    public PDPCurrentSensor(PowerDistributionPanel pdp, int port) {
        this.pdp = pdp;
        this.port = port;
    }

    public double getCurrent() {
        return pdp.getCurrent(port);
    }
}
