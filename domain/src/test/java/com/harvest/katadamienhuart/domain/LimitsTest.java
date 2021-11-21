package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LimitsTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(Limits.class).verify();
    }

    @Test
    public void should_fail_fast_when_cold_limit_is_null() {
        assertThatThrownBy(() -> new Limits(null, new WarnLimit(new DegreeCelsius(40))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_warn_limit_is_null() {
        assertThatThrownBy(() -> new Limits(new ColdLimit(new DegreeCelsius(22)), null))
                .isInstanceOf(NullPointerException.class);
    }

}
