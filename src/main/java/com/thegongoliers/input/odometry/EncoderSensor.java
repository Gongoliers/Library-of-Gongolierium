package com.thegongoliers.input.odometry;

import java.util.function.DoubleSupplier;

import com.thegongoliers.utils.Resettable;
public interface EncoderSensor extends DistanceSensor, VelocitySensor, Resettable {}
