package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarnLimitTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(WarnLimit.class).verify();
    }

    @Test
    public void should_fail_fast_when_limit_is_null() {
        assertThatThrownBy(() -> new WarnLimit(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_when_limit_is_not_positive() {
        assertThatThrownBy(() -> new WarnLimit(new DegreeCelsius(-10)))
                .isInstanceOf(WarnLimitMustBePositiveException.class)
                .hasFieldOrPropertyWithValue("limit", new DegreeCelsius(-10));
    }

}
