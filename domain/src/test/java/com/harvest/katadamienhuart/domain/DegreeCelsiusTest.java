package com.harvest.katadamienhuart.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DegreeCelsiusTest {

    @Test
    void should_fail_fast_when_temperature_is_null() {
        assertThatThrownBy(() -> new DegreeCelsius(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Nested
    class IsGreaterThanOrEquals {

        @Test
        void should_be_greater() {
            assertThat(new DegreeCelsius(10).isGreaterThanOrEquals(new DegreeCelsius(9))).isTrue();
        }

        @Test
        void should_not_be_greater() {
            assertThat(new DegreeCelsius(9).isGreaterThanOrEquals(new DegreeCelsius(10))).isFalse();
        }

        @Test
        void should_be_equals() {
            assertThat(new DegreeCelsius(9).isGreaterThanOrEquals(new DegreeCelsius(9))).isTrue();
        }

    }

    @Nested
    class IsBeforeThan {

        @Test
        void should_be_before() {
            assertThat(new DegreeCelsius(9).isBeforeThan(new DegreeCelsius(10))).isTrue();
        }

        @Test
        void should_not_be_before() {
            assertThat(new DegreeCelsius(10).isBeforeThan(new DegreeCelsius(9))).isFalse();
        }

        @Test
        void should_not_be_before_when_equals() {
            assertThat(new DegreeCelsius(10).isBeforeThan(new DegreeCelsius(10))).isFalse();
        }
    }

    @Nested
    class IsPositive {

        @Test
        void should_be_positive() {
            assertThat(new DegreeCelsius(10).isPositive()).isTrue();
        }

        @Test
        void should_be_negative() {
            assertThat(new DegreeCelsius(-10).isPositive()).isFalse();
        }

        @Test
        void should_not_be_positive_nor_negative() {
            assertThat(new DegreeCelsius(0).isPositive()).isFalse();
        }

    }

}