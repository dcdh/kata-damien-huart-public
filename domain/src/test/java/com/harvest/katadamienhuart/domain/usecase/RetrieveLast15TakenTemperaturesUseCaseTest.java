package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.SensorHistory;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import com.harvest.katadamienhuart.domain.spi.TakenTemperatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RetrieveLast15TakenTemperaturesUseCaseTestResolver.class)
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

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.ThreeTakenTemperaturesTest
    void should_return_last_15_taken_temperature(final Limits limits,
                                                 final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures,
                                                 final RetrieveLast15TakenTemperaturesUseCaseTestResolver.SensorHistories expectedSensorHistories)
            throws RetrieveLast15TakenTemperaturesException {
        // Given
        doReturn(Optional.of(limits)).when(limitsRepository).findLastLimits();
        doReturn(givenTakenTemperatures.takenTemperatures()).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertAll(
                () -> assertThat(sensorHistories).containsAll(expectedSensorHistories.sensorHistories()),
                () -> verify(limitsRepository, times(1)).findLastLimits(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.ThreeTakenTemperaturesTest
    void should_use_default_limits_when_not_defined(final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures,
                                                    final RetrieveLast15TakenTemperaturesUseCaseTestResolver.SensorHistories expectedSensorHistories)
            throws RetrieveLast15TakenTemperaturesException {
        // Given
        doReturn(givenTakenTemperatures.takenTemperatures()).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertAll(
                () -> assertThat(sensorHistories).containsAll(expectedSensorHistories.sensorHistories()),
                () -> verify(limitsRepository, times(1)).findLastLimits(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.TwentyTakenTemperaturesTest
    public void should_fail_fast_if_more_than_15_are_returned(final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures) {
        // Given
        doReturn(givenTakenTemperatures.takenTemperatures()).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When & Then
        assertThatThrownBy(() -> retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest()))
                .isInstanceOf(RetrieveLast15TakenTemperaturesException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Max 15 taken temperature expected");
    }

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.UnorderedTakenTemperaturesTest
    public void should_fail_fast_when_history_is_not_sorted_desc(final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures) {
        // Given
        doReturn(givenTakenTemperatures.takenTemperatures()).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When & Then
        assertThatThrownBy(() -> retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest()))
                .isInstanceOf(RetrieveLast15TakenTemperaturesException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Taken temperature history is not sorted descending");
    }

    @Test
    public void should_handle_unknown_exception() {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When
        assertThatThrownBy(() -> retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest()))
                .isInstanceOf(RetrieveLast15TakenTemperaturesException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Unknown exception happened");
    }
}