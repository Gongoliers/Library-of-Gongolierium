package com.thegongoliers.output;

import com.thegongoliers.input.CurrentSensor;
import com.thegongoliers.input.Switch;

public interface Compressor {
	Compressor start();

	Compressor stop();

	Compressor runAutomatically();

	Switch getPressureSwitch();

	boolean isRunning();
	
	CurrentSensor getCompressorCurrent();
}
