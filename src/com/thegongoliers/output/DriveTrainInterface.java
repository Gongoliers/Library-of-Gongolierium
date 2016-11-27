package com.thegongoliers.output;

import edu.wpi.first.wpilibj.GenericHID;

public interface DriveTrainInterface extends Stoppable {
	void forward(double speed);

	void reverse(double speed);

	void rotateCounterClockwise(double speed);

	void rotateClockwise(double speed);

	void operate(GenericHID controller);
}
