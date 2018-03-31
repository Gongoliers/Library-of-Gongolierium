package com.thegongoliers.input.odometry;

import com.thegongoliers.annotations.Untested;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

@Untested
public class FRCEncoder implements IEncoder {

    private Encoder encoder;
    private boolean inverted = false;

    public FRCEncoder(final int channelA, final int channelB){
        encoder = new Encoder(channelA, channelB);
    }

    public FRCEncoder(final int channelA, final int channelB, boolean reverseDirection){
        encoder = new Encoder(channelA, channelB, reverseDirection);
        inverted = reverseDirection;
    }

    public FRCEncoder(final int channelA, final int channelB, boolean reverseDirection,
                      final CounterBase.EncodingType encodingType){
        encoder = new Encoder(channelA, channelB, reverseDirection, encodingType);
        inverted = reverseDirection;
    }

    public FRCEncoder(final int channelA, final int channelB, final int indexChannel,
                      boolean reverseDirection){
        encoder = new Encoder(channelA, channelB, indexChannel, reverseDirection);
        inverted = reverseDirection;
    }

    public FRCEncoder(final int channelA, final int channelB, final int indexChannel){
        encoder = new Encoder(channelA, channelB, indexChannel);
    }

    public FRCEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection){
        encoder = new Encoder(sourceA, sourceB, reverseDirection);
        inverted = reverseDirection;
    }

    public FRCEncoder(DigitalSource sourceA, DigitalSource sourceB){
        encoder = new Encoder(sourceA, sourceB);
    }

    public FRCEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection,
                      final CounterBase.EncodingType encodingType){
        encoder = new Encoder(sourceA, sourceB, reverseDirection, encodingType);
        inverted = reverseDirection;
    }

    public FRCEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource,
                      boolean reverseDirection){
        encoder = new Encoder(sourceA, sourceB, indexSource, reverseDirection);
        inverted = reverseDirection;
    }

    public FRCEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource){
        encoder = new Encoder(sourceA, sourceB, indexSource);
    }

    @Override
    public double getPulses() {
        return encoder.get();
    }

    @Override
    public double getDistance() {
        return encoder.getDistance();
    }

    @Override
    public double getVelocity() {
        return encoder.getRate();
    }

    @Override
    public void setDistancePerPulse(double distancePerPulse) {
        encoder.setDistancePerPulse(distancePerPulse);

    }

    @Override
    public void reset() {
        encoder.reset();
    }

    @Override
    public void setInverted(boolean inverted) {
        encoder.setReverseDirection(inverted);
        this.inverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }
}
