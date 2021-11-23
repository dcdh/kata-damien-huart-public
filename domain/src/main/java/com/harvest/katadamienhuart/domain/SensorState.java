package com.harvest.katadamienhuart.domain;

import java.util.stream.Stream;

public enum SensorState {

    COLD {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return sensedTemperature.isBeforeThan(coldLimit.limit());
        }

    },

    WARM {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return sensedTemperature.isGreaterThanOrEquals(coldLimit.limit()) &&
                    sensedTemperature.isBeforeThan(warmLimit.limit());
        }

    },

    HOT {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return sensedTemperature.isGreaterThanOrEquals(warmLimit.limit());
        }

    };

    public abstract boolean matchState(DegreeCelsius sensedTemperature, ColdLimit coldLimit, WarmLimit warmLimit);

    public static final SensorState fromSensedTemperature(final DegreeCelsius sensedTemperature, final Limits limits) {
        return Stream.of(SensorState.values())
                .filter(sensorState -> sensorState.matchState(sensedTemperature, limits.coldLimit(), limits.warmLimit()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here"));
    }

}
