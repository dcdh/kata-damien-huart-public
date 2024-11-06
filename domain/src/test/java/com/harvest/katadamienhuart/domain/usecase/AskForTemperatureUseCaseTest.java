package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import com.harvest.katadamienhuart.domain.spi.TakenAtProvider;
import com.harvest.katadamienhuart.domain.spi.TakenTemperatureRepository;
import com.harvest.katadamienhuart.domain.spi.TemperatureCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AskForTemperatureUseCaseTestResolver.class)
class AskForTemperatureUseCaseTest {

    @Mock
    TemperatureCaptor temperatureCaptor;

    @Mock
    TakenAtProvider takenAtProvider;

    @Mock
    TakenTemperatureRepository takenTemperatureRepository;

    @Mock
    LimitsRepository limitsRepository;

    private AskForTemperatureUseCase askForTemperatureUseCase;

    @BeforeEach
    void setup() {
        askForTemperatureUseCase = new AskForTemperatureUseCase(temperatureCaptor, takenAtProvider, takenTemperatureRepository, limitsRepository);
    }

    @Test
    void should_ask_for_temperature(final Temperature temperature,
                                    final TakenAt takenAt,
                                    final Limits limits,
                                    final Sensor expectedSensor) throws AskForTemperatureException {
        // Given
        doReturn(temperature).when(temperatureCaptor).takeTemperature();
        doReturn(takenAt).when(takenAtProvider).now();
        doReturn(Optional.of(limits)).when(limitsRepository).findLastLimits();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureRequest());

        // Then
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(temperatureCaptor, times(1)).takeTemperature(),
                () -> verify(takenTemperatureRepository, times(1))
                        .store(new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC)))),
                () -> verify(takenAtProvider, times(1)).now(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @Test
    void should_use_default_limits_when_not_defined(final Temperature temperature, final TakenAt takenAt,
                                                    final Sensor expectedSensor) throws AskForTemperatureException {
        // Given
        doReturn(temperature).when(temperatureCaptor).takeTemperature();
        doReturn(takenAt).when(takenAtProvider).now();
        doReturn(Optional.empty()).when(limitsRepository).findLastLimits();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureRequest());

        // Then
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(temperatureCaptor, times(1)).takeTemperature(),
                () -> verify(takenTemperatureRepository, times(1))
                        .store(new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC)))),
                () -> verify(takenAtProvider, times(1)).now(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @Test
    public void should_handle_unknown_exception() {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(limitsRepository).findLastLimits();

        // When
        assertThatThrownBy(() -> askForTemperatureUseCase.execute(new AskForTemperatureRequest()))
                .isInstanceOf(AskForTemperatureException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Unknown exception happened");
    }
}
