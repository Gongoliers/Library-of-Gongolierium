package com.thegongoliers.input;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Created by Kyle on 7/25/2016.
 */
public class PDPCurrentSensor implements PIDSource, CurrentSensor {

	private int port;
	private PowerDistributionPanel pdp;

	public PDPCurrentSensor(PowerDistributionPanel pdp, int port) {
		this.pdp = pdp;
		this.port = port;
	}

	public double getCurrent() {
		return pdp.getCurrent(port);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getCurrent();
	}

}
