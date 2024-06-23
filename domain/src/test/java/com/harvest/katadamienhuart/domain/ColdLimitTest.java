package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ColdLimitTest {

    @Test
    public void should_fail_fast_when_limit_is_null() {
        assertThatThrownBy(() -> new ColdLimit(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_when_limit_is_not_positive() {
        assertThatThrownBy(() -> new ColdLimit(new DegreeCelsius(-10)))
                .isInstanceOf(ColdLimitMustBePositiveException.class)
                .hasFieldOrPropertyWithValue("limit", new DegreeCelsius(-10));
    }

}
