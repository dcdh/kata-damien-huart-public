package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SensorTest {

    @Test
    public void should_fail_fast_when_sensed_at_is_null() {
        assertThatThrownBy(() -> new Sensor(null, new TakenTemperature(new DegreeCelsius(10)), new Limits(new ColdLimit(new DegreeCelsius(22)), new WarmLimit(new DegreeCelsius(22)))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_sensed_temperature_is_null() {
        assertThatThrownBy(() -> new Sensor(new TakenAt(ZonedDateTime.now()), null, new Limits(new ColdLimit(new DegreeCelsius(22)), new WarmLimit(new DegreeCelsius(22)))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_limits_is_null() {
        assertThatThrownBy(() -> new Sensor(new TakenAt(ZonedDateTime.now()), new TakenTemperature(new DegreeCelsius(10)), (Limits) null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_sensor_state_is_null() {
        assertThatThrownBy(() -> new Sensor(new TakenAt(ZonedDateTime.now()), new TakenTemperature(new DegreeCelsius(10)), (SensorState) null))
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
    public void should_return_expected_state(final Integer givenSensedTemperature, final SensorState expectedSensorState) {
        assertThat(new Sensor(new TakenAt(ZonedDateTime.now()),
                new TakenTemperature(new DegreeCelsius(givenSensedTemperature)),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))).sensorState()).isEqualTo(expectedSensorState);
    }

    @Test
    public void should_fail_when_warm_limit_is_before_cold_limit() {
        assertThatThrownBy(() -> new Sensor(new TakenAt(ZonedDateTime.now()),
                new TakenTemperature(new DegreeCelsius(0)),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(20)))).sensorState())
                .isInstanceOf(WarmLimitMustBeSuperiorToColdLimitException.class)
                .hasFieldOrPropertyWithValue("coldLimit", new ColdLimit(new DegreeCelsius(22)))
                .hasFieldOrPropertyWithValue("warmLimit", new WarmLimit(new DegreeCelsius(20)));
    }

}
