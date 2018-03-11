package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderUtils {

    public static void addGearRatio(Encoder encoder, double gearRatio){
        double distancePerPulse = encoder.getDistancePerPulse();
        encoder.setDistancePerPulse(distancePerPulse * gearRatio);
    }

    public static void addMechanism(Encoder encoder, double distancePerRevolution){
        double degPerPulse = encoder.getDistancePerPulse();
        double distancePerDegree = distancePerRevolution / 360.0;
        encoder.setDistancePerPulse(distancePerDegree * degPerPulse);
    }

    public static void setPulsesPerRevolution(Encoder encoder, double pulses){
        double degreesPerPulse = pulses / 360.0;
        encoder.setDistancePerPulse(degreesPerPulse);
    }

    public static void addWheel(Encoder encoder, double diameter){
        double distancePerRevolution = diameter * Math.PI;
        addMechanism(encoder, distancePerRevolution);
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

        public Encoder build(){
            return encoder;
        }

    }

}
