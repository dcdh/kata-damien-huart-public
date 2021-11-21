package com.harvest.katadamienhuart.domain;

import java.util.stream.Stream;

public enum SensorState {

    COLD {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature) {
            return sensedTemperature.isBeforeThan(new DegreeCelsius(22));
        }

    },

    WARM {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature) {
            return sensedTemperature.isGreaterThanOrEquals(new DegreeCelsius(22)) &&
                    sensedTemperature.isBeforeThan(new DegreeCelsius(40));
        }

    },

    HOT {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature) {
            return sensedTemperature.isGreaterThanOrEquals(new DegreeCelsius(40));
        }

    };

    public abstract boolean matchState(DegreeCelsius sensedTemperature);

    public static final SensorState fromSensedTemperature(DegreeCelsius sensedTemperature) {
        return Stream.of(SensorState.values())
                .filter(sensorState -> sensorState.matchState(sensedTemperature))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here"));
    }

}
