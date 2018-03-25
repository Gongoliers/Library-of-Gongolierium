package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderUtils {

    public static void addGearRatio(Encoder encoder, double gearRatio){
        double distancePerPulse = encoder.getDistancePerPulse();
        encoder.setDistancePerPulse(distancePerPulse * gearRatio);
    }

    public static void addMechanism(Encoder encoder, double distancePerRevolution){
        encoder.setDistancePerPulse(distancePerRevolution * encoder.getDistancePerPulse());
    }

    public static void setPulsesPerRevolution(Encoder encoder, double pulses){
        if(pulses != 0) {
            encoder.setDistancePerPulse(1 / pulses);
        }
    }

    public static void addWheel(Encoder encoder, double diameter){
        double distancePerRevolution = diameter * Math.PI;
        addMechanism(encoder, distancePerRevolution);
    }

    public static void addFudgeFactor(Encoder encoder, double factor){
        encoder.setDistancePerPulse(encoder.getDistancePerPulse() * factor);
    }

    public class EncoderDistanceBuilder {
        private Encoder encoder;

        public EncoderDistanceBuilder(Encoder encoder, double encoderPulsesPerRevolution){
            this.encoder = encoder;
            EncoderUtils.setPulsesPerRevolution(encoder, encoderPulsesPerRevolution);
        }

        public EncoderDistanceBuilder addGearRatio(double gearRatio){
            EncoderUtils.addGearRatio(encoder, gearRatio);
            return this;
        }

        public EncoderDistanceBuilder addMechanism(double distancePerRevolution){
            EncoderUtils.addMechanism(encoder, distancePerRevolution);
            return this;
        }

        public EncoderDistanceBuilder addWheel(double diameter){
            EncoderUtils.addWheel(encoder, diameter);
            return this;
        }

        public EncoderDistanceBuilder addFudgeFactor(double fudgeFactor){
            EncoderUtils.addFudgeFactor(encoder, fudgeFactor);
            return this;
        }

        public Encoder build(){
            return encoder;
        }

    }

}
