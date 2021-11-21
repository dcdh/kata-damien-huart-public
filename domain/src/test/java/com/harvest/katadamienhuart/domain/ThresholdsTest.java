package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ThresholdsTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(Thresholds.class).verify();
    }

    @Test
    public void should_fail_fast_when_cold_threshold_is_null() {
        assertThatThrownBy(() -> new Thresholds(null, new WarnThreshold(new DegreeCelsius(40))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_warn_threshold_is_null() {
        assertThatThrownBy(() -> new Thresholds(new ColdThreshold(new DegreeCelsius(22)), null))
                .isInstanceOf(NullPointerException.class);
    }

}
