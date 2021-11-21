package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DegreeCelsiusTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(DegreeCelsius.class).verify();
    }

    @Test
    public void should_fail_fast_when_temperature_is_null() {
        assertThatThrownBy(() -> new DegreeCelsius(null))
                .isInstanceOf(NullPointerException.class);
    }

}