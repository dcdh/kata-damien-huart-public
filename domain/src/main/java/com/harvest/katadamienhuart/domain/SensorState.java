package com.harvest.katadamienhuart.domain;

import java.util.stream.Stream;

public enum SensorState {

    COLD {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdThreshold coldThreshold, final WarnThreshold warnThreshold) {
            return sensedTemperature.isBeforeThan(coldThreshold.threshold());
        }

    },

    WARM {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdThreshold coldThreshold, final WarnThreshold warnThreshold) {
            return sensedTemperature.isGreaterThanOrEquals(coldThreshold.threshold()) &&
                    sensedTemperature.isBeforeThan(warnThreshold.threshold());
        }

    },

    HOT {

        @Override
        public boolean matchState(final DegreeCelsius sensedTemperature, final ColdThreshold coldThreshold, final WarnThreshold warnThreshold) {
            return sensedTemperature.isGreaterThanOrEquals(warnThreshold.threshold());
        }

    };

    public abstract boolean matchState(DegreeCelsius sensedTemperature, ColdThreshold coldThreshold, WarnThreshold warnThreshold);

    public static final SensorState fromSensedTemperature(final DegreeCelsius sensedTemperature, final Thresholds thresholds) {
        return Stream.of(SensorState.values())
                .filter(sensorState -> sensorState.matchState(sensedTemperature, thresholds.coldThreshold(), thresholds.warnThreshold()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here"));
    }

}
