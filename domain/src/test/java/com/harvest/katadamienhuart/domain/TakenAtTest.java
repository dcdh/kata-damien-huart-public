package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TakenAtTest {

    @Test
    public void should_fail_fast_when_at_is_null() {
        assertThatThrownBy(() -> new TakenAt(null))
                .isInstanceOf(NullPointerException.class);
    }

}