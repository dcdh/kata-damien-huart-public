package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.WarmLimit;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RedefineLimitsCommandTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(RedefineLimitsCommand.class).verify();
    }

    @Test
    public void should_fail_fast_when_new_cold_limit_is_null() {
        assertThatThrownBy(() -> new RedefineLimitsCommand(null, new WarmLimit(new DegreeCelsius(40))))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_fast_when_new_warm_limit_is_null() {
        assertThatThrownBy(() -> new RedefineLimitsCommand(new ColdLimit(new DegreeCelsius(22)), null))
                .isInstanceOf(NullPointerException.class);
    }

}
