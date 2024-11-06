package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.Objects;

@Schema(name = "Sensor", required = true, requiredProperties = {"sensorState", "takenTemperature", "takenAt"})
public record SensorDTO(SensorState sensorState, Integer takenTemperature, ZonedDateTime takenAt) {
    public SensorDTO {
        Objects.requireNonNull(sensorState);
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(takenAt);
    }

    public SensorDTO(final Sensor sensor) {
        this(sensor.sensorState(),
                sensor.takenTemperature().temperature().degreeCelsius().temperature(),
                sensor.takenTemperature().takenAt().at());
    }

}
