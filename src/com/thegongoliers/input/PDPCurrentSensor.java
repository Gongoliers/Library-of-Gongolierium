package com.thegongoliers.input;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDPCurrentSensor implements CurrentSensor {

	private int port;
	private PowerDistributionPanel pdp;

	/**
	 * Creates a current sensor which pulls its data from the specified port of
	 * the PDP
	 * 
	 * @param pdp
	 *            The PDP on the robot
	 * @param port
	 *            The port on the PDP
	 */
	public PDPCurrentSensor(PowerDistributionPanel pdp, int port) {
		this.pdp = pdp;
		this.port = port;
	}

	public double getCurrent() {
		return pdp.getCurrent(port);
	}

}
