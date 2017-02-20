package com.thegongoliers.input.current;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDPCurrentSensor implements CurrentSensor {

	private PowerDistributionPanel pdp;
	private int port;

	/**
	 * Creates a current sensor at the specified port of the main PDP
	 * 
	 * @param port
	 *            The port on the PDP to monitor
	 */
	public PDPCurrentSensor(int port) {
		pdp = new PowerDistributionPanel();
		this.port = port;
	}

	/**
	 * Creates a current sensor at the specified port of the specified PDP
	 * 
	 * @param pdp
	 *            The PDP that contains the specified port
	 * @param port
	 *            The port on the PDP to monitor
	 */
	public PDPCurrentSensor(PowerDistributionPanel pdp, int port) {
		this.pdp = pdp;
		this.port = port;
	}

	@Override
	public double getCurrent() {
		return pdp.getCurrent(port);
	}

}
