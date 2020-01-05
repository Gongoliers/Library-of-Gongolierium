package com.thegongoliers.input.switches;

import com.thegongoliers.annotations.UsedInCompetition;
import edu.wpi.first.wpilibj.DigitalInput;

@UsedInCompetition(team = "5112", year = "2019")
public class LimitSwitch implements Switch {

	private final DigitalInput input;

	/**
	 * Defined a limit switch. By default it is normally open which means
	 * isTriggered is true when the switch is closed.
	 * 
	 * @param port
	 *            The port that the switch is connected to.
	 */
	public LimitSwitch(int port) {
		input = new DigitalInput(port);
	}

	@Override
	public boolean isTriggered() {
		return input.get();
	}

}
