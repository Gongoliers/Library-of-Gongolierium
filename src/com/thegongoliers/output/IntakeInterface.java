package com.thegongoliers.output;

public interface IntakeInterface extends Stoppable {
	void in(double speed);

	void in();

	void out(double speed);

	void out();
}
