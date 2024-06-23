package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LimitsTest {

    @Test
    public void should_fail_fast_when_cold_limit_is_null() {
        assertThatThrownBy(() -> new Limits(null, new WarmLimit(new DegreeCelsius(40))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_warm_limit_is_null() {
        assertThatThrownBy(() -> new Limits(new ColdLimit(new DegreeCelsius(22)), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_be_22_40_by_default() {
        final Limits givenLimit = Limits.ofDefault();
        assertAll(() -> assertThat(givenLimit).isEqualTo(new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))),
                () -> assertThat(givenLimit.coldLimit()).isEqualTo(new ColdLimit(new DegreeCelsius(22))),
                () -> assertThat(givenLimit.warmLimit()).isEqualTo(new WarmLimit(new DegreeCelsius(40))));
    }
}
