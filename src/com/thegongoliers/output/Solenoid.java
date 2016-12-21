package com.thegongoliers.output;

public interface Solenoid {
	Solenoid extend();

	Solenoid retract();

	boolean isExtended();

	boolean isRetracted();

	default public Solenoid invert() {
		Solenoid parent = this;
		return new Solenoid() {

			@Override
			public Solenoid retract() {
				return parent.extend();
			}

			@Override
			public boolean isRetracted() {
				return parent.isExtended();
			}

			@Override
			public boolean isExtended() {
				return parent.isRetracted();
			}

			@Override
			public Solenoid extend() {
				return parent.retract();
			}
		};
	}
}
