package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SensorTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(Sensor.class).verify();
    }

    @ParameterizedTest
    @CsvSource({
            "21,COLD",
            "22,WARM",
            "23,WARM",
            "24,WARM",
            "25,WARM",
            "26,WARM",
            "27,WARM",
            "28,WARM",
            "29,WARM",
            "30,WARM",
            "31,WARM",
            "32,WARM",
            "33,WARM",
            "34,WARM",
            "35,WARM",
            "36,WARM",
            "37,WARM",
            "38,WARM",
            "39,WARM",
            "40,HOT"})
    public void should_return_expected_state(final Integer givenSensedTemperature, final SensorState expectedSensorState) {
        assertThat(new Sensor(new DegreeCelsius(givenSensedTemperature)).sensorState()).isEqualTo(expectedSensorState);
    }

}
