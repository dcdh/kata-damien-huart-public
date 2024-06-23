package com.harvest.katadamienhuart.domain;

import java.util.stream.Stream;

public enum SensorState {

    COLD {

        @Override
        public boolean matchState(final TakenTemperature takenTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return takenTemperature.isBeforeThan(coldLimit.limit());
        }

    },

    WARM {

        @Override
        public boolean matchState(final TakenTemperature takenTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return takenTemperature.isGreaterThanOrEquals(coldLimit.limit()) &&
                    takenTemperature.isBeforeThan(warmLimit.limit());
        }

    },

    HOT {

        @Override
        public boolean matchState(final TakenTemperature takenTemperature, final ColdLimit coldLimit, final WarmLimit warmLimit) {
            return takenTemperature.isGreaterThanOrEquals(warmLimit.limit());
        }

    };

    public abstract boolean matchState(TakenTemperature takenTemperature, ColdLimit coldLimit, WarmLimit warmLimit);

    public static SensorState fromSensedTemperature(final TakenTemperature takenTemperature, final Limits limits) {
        return Stream.of(SensorState.values())
                .filter(sensorState -> sensorState.matchState(takenTemperature, limits.coldLimit(), limits.warmLimit()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here"));
    }

}
