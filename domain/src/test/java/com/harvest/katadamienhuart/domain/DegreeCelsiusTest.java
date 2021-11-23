package com.harvest.katadamienhuart.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Nested
    public static class IsGreaterThanOrEquals {

        @Test
        public void should_be_greater() {
            assertThat(new DegreeCelsius(10).isGreaterThanOrEquals(new DegreeCelsius(9))).isTrue();
        }

        @Test
        public void should_not_be_greater() {
            assertThat(new DegreeCelsius(9).isGreaterThanOrEquals(new DegreeCelsius(10))).isFalse();
        }

        @Test
        public void should_be_equals() {
            assertThat(new DegreeCelsius(9).isGreaterThanOrEquals(new DegreeCelsius(9))).isTrue();
        }

    }

    @Nested
    public static class IsBeforeThan {

        @Test
        public void should_be_before() {
            assertThat(new DegreeCelsius(9).isBeforeThan(new DegreeCelsius(10))).isTrue();
        }

        @Test
        public void should_not_be_before() {
            assertThat(new DegreeCelsius(10).isBeforeThan(new DegreeCelsius(9))).isFalse();
        }

        @Test
        public void should_not_be_before_when_equals() {
            assertThat(new DegreeCelsius(10).isBeforeThan(new DegreeCelsius(10))).isFalse();
        }
    }

    @Nested
    public static class IsPositive {

        @Test
        public void should_be_positive() {
            assertThat(new DegreeCelsius(10).isPositive()).isTrue();
        }

        @Test
        public void should_be_negative() {
            assertThat(new DegreeCelsius(-10).isPositive()).isFalse();
        }

        @Test
        public void should_not_be_positive_nor_negative() {
            assertThat(new DegreeCelsius(0).isPositive()).isFalse();
        }

    }

}