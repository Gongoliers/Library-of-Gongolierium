package com.thegongoliers.input;

public interface Fuse extends Switch {
	Fuse trigger();

	Fuse reset();

	public static Fuse create() {
		return new Fuse() {

			private boolean triggered = false;

			@Override
			public boolean isTriggered() {
				return triggered;
			}

			@Override
			public Fuse trigger() {
				triggered = true;
				return this;
			}

			@Override
			public Fuse reset() {
				triggered = false;
				return this;
			}

		};
	}
}
