package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveLast15TakenTemperaturesUseCaseTest {

    @Mock
    TakenTemperatureRepository takenTemperatureRepository;

    @Mock
    LimitsRepository limitsRepository;

    RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase;

    @BeforeEach
    void setup() {
        retrieveLast15TakenTemperaturesUseCase = new RetrieveLast15TakenTemperaturesUseCase(limitsRepository, takenTemperatureRepository);
    }

    @Test
    void should_return_last_15_taken_temperature() throws RetrieveLast15TakenTemperaturesException {
        // Given
        doReturn(Optional.of(TestProvider.GIVEN_LIMITS)).when(limitsRepository).findLastLimits();
        doReturn(TestProvider.GIVEN_TAKEN_TEMPERATURES).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertAll(
                () -> assertThat(sensorHistories).containsExactly(
                        new SensorHistory(new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 2, 0, 0, 0, 0, ZoneOffset.UTC))),
                                SensorState.COLD),
                        new SensorHistory(new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                                SensorState.COLD)
                ),
                () -> verify(limitsRepository, times(1)).findLastLimits(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @Test
    void should_use_default_limits_when_not_defined() throws RetrieveLast15TakenTemperaturesException {
        // Given
        doReturn(TestProvider.GIVEN_TAKEN_TEMPERATURES).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertAll(
                () -> assertThat(sensorHistories).containsExactly(
                        new SensorHistory(new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 2, 0, 0, 0, 0, ZoneOffset.UTC))),
                                SensorState.COLD),
                        new SensorHistory(new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                                SensorState.COLD)
                ),
                () -> verify(limitsRepository, times(1)).findLastLimits(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @Test
    public void should_fail_fast_if_more_than_15_are_returned() {
        // Given
        final List<TakenTemperature> givenTakenTemperatures = IntStream.range(1, 20).boxed()
                .sorted(Collections.reverseOrder())
                .map(dayOfMonth ->
                        new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
                )
                .toList();
        doReturn(givenTakenTemperatures).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When & Then
        assertThatThrownBy(() -> retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Max 15 taken temperature expected");
    }

    @Test
    public void should_fail_fast_when_history_is_not_sorted_desc() {
        // Given
        final List<TakenTemperature> givenTakenTemperatures = IntStream.range(1, 10).boxed()
                .map(dayOfMonth ->
                        new TakenTemperature(
                                new Temperature(new DegreeCelsius(20)),
                                new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
                )
                .toList();
        doReturn(givenTakenTemperatures).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When & Then
        assertThatThrownBy(() -> retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Taken temperature history is not sorted descending");
    }

}