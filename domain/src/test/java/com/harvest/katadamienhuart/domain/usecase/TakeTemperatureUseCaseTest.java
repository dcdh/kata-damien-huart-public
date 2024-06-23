package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TakeTemperatureUseCaseTest {

    private TemperatureCaptor temperatureCaptor;
    private SensedAtProvider sensedAtProvider;
    private SensorRepository sensorRepository;
    private LimitsRepository limitsRepository;
    private TakeTemperatureUseCase takeTemperatureUseCase;

    @BeforeEach
    public void setup() {
        temperatureCaptor = mock(TemperatureCaptor.class);
        sensedAtProvider = mock(SensedAtProvider.class);
        sensorRepository = mock(SensorRepository.class);
        limitsRepository = mock(LimitsRepository.class);
        takeTemperatureUseCase = new TakeTemperatureUseCase(temperatureCaptor, sensedAtProvider, sensorRepository, limitsRepository);
    }

    @Test
    public void should_take_temperature() {
        // Given
        doReturn(new DegreeCelsius(30)).when(temperatureCaptor).takeTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new SensedAt(sensedAt)).when(sensedAtProvider).now();
        doReturn(new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(40))
        )).when(limitsRepository).getLimits();

        // When
        final Sensor sensor = takeTemperatureUseCase.execute(new TakeTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new SensedAt(sensedAt),
                new DegreeCelsius(30),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertThat(sensor).isEqualTo(expectedSensor);
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    public void should_use_default_limits_when_not_defined() {
        // Given
        doReturn(new DegreeCelsius(30)).when(temperatureCaptor).takeTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new SensedAt(sensedAt)).when(sensedAtProvider).now();

        // When
        final Sensor sensor = takeTemperatureUseCase.execute(new TakeTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new SensedAt(sensedAt),
                new DegreeCelsius(30),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertThat(sensor).isEqualTo(expectedSensor);
    }
}
