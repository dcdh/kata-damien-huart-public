package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.DegreeCelsius;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RedefineLimitsRequestTest {

    @Test
    void should_fail_fast_when_new_cold_limit_is_null() {
        assertThatThrownBy(() -> new RedefineLimitsRequest(null, new NewWarmLimit(new DegreeCelsius(40))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_fail_fast_when_new_warm_limit_is_null() {
        assertThatThrownBy(() -> new RedefineLimitsRequest(new NewColdLimit(new DegreeCelsius(22)), null))
                .isInstanceOf(NullPointerException.class);
    }

}
