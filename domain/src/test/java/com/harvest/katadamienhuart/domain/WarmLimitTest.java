package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarmLimitTest {

    @Test
    public void should_fail_fast_when_limit_is_null() {
        assertThatThrownBy(() -> new WarmLimit(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_when_limit_is_not_positive() {
        assertThatThrownBy(() -> new WarmLimit(new DegreeCelsius(-10)))
                .isInstanceOf(WarmLimitMustBePositiveException.class)
                .hasFieldOrPropertyWithValue("limit", new DegreeCelsius(-10));
    }

}
