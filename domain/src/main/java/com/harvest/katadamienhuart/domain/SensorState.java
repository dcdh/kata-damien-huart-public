package com.harvest.katadamienhuart.domain;

import java.util.stream.Stream;

public enum SensorState {

    COLD {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarnLimit warnLimit) {
            return sensedTemperature.isBeforeThan(coldLimit.limit());
        }

    },

    WARM {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarnLimit warnLimit) {
            return sensedTemperature.isGreaterThanOrEquals(coldLimit.limit()) &&
                    sensedTemperature.isBeforeThan(warnLimit.limit());
        }

    },

    HOT {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdLimit coldLimit, final WarnLimit warnLimit) {
            return sensedTemperature.isGreaterThanOrEquals(warnLimit.limit());
        }

    };

    public abstract boolean matchState(DegreeCelsius sensedTemperature, ColdLimit coldLimit, WarnLimit warnLimit);

    public static final SensorState fromSensedTemperature(final DegreeCelsius sensedTemperature, final Limits limits) {
        return Stream.of(SensorState.values())
                .filter(sensorState -> sensorState.matchState(sensedTemperature, limits.coldLimit(), limits.warnLimit()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here"));
    }

}
