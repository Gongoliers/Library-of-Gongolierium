package com.thegongoliers.output;

public interface Intake extends Stoppable {
	void in(double speed);

	void in();

	void out(double speed);

	void out();
}
