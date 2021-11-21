package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SensedAtTest {

    @Test
    public void should_verify_equality() {
        EqualsVerifier.forClass(SensedAt.class).verify();
    }

    @Test
    public void should_fail_fast_when_at_is_null() {
        assertThatThrownBy(() -> new SensedAt(null))
                .isInstanceOf(NullPointerException.class);
    }

}