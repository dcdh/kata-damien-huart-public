package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SensorTest {

    @Test
    void should_fail_fast_when_taken_temperature_is_null() {
        assertThatThrownBy(() -> new Sensor(null, new Limits(new ColdLimit(new DegreeCelsius(22)), new WarmLimit(new DegreeCelsius(22)))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_fail_fast_when_limits_is_null() {
        assertThatThrownBy(() -> new Sensor(
                new TakenTemperature(
                        new Temperature(new DegreeCelsius(10)),
                        new TakenAt(ZonedDateTime.now())),
                null))
                .isInstanceOf(NullPointerException.class);
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
    void should_return_expected_state(final Integer givenTakenTemperature, final SensorState expectedSensorState) {
        assertThat(new Sensor(
                new TakenTemperature(
                        new Temperature(new DegreeCelsius(givenTakenTemperature)),
                        new TakenAt(ZonedDateTime.now())),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))).sensorState()).isEqualTo(expectedSensorState);
    }

}
