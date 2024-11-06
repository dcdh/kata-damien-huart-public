package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.SensorHistory;
import com.harvest.katadamienhuart.domain.SensorState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.Objects;

@Schema(name = "SensorHistory", required = true, requiredProperties = {"sensorState", "takenTemperature", "takenAt"})
public record SensorHistoryDTO(SensorState sensorState, Integer takenTemperature, ZonedDateTime takenAt) {
    public SensorHistoryDTO {
        Objects.requireNonNull(sensorState);
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(takenAt);
    }

    public SensorHistoryDTO(final SensorHistory sensorHistory) {
        this(
                sensorHistory.sensorState(),
                sensorHistory.takenTemperature().temperature().degreeCelsius().temperature(),
                sensorHistory.takenTemperature().takenAt().at()
        );
    }
}
