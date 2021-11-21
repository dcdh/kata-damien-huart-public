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
public class GetTemperatureUseCaseTest {

    private TemperatureCaptor temperatureCaptor;
    private SensedAtProvider sensedAtProvider;
    private SensorRepository sensorRepository;
    private LimitsRepository limitsRepository;
    private GetTemperatureUseCase getTemperatureUseCase;

    @BeforeEach
    public void setup() {
        temperatureCaptor = mock(TemperatureCaptor.class);
        sensedAtProvider = mock(SensedAtProvider.class);
        sensorRepository = mock(SensorRepository.class);
        limitsRepository = mock(LimitsRepository.class);
        getTemperatureUseCase = new GetTemperatureUseCase(temperatureCaptor, sensedAtProvider, sensorRepository, limitsRepository);
    }

    @Test
    public void should_get_temperature() {
        // Given
        doReturn(new DegreeCelsius(30)).when(temperatureCaptor).getTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new SensedAt(sensedAt)).when(sensedAtProvider).now();
        doReturn(new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarnLimit(new DegreeCelsius(40))
        )).when(limitsRepository).getLimits();

        // When
        final Sensor sensor = getTemperatureUseCase.execute(new GetTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new SensedAt(sensedAt),
                new DegreeCelsius(30),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarnLimit(new DegreeCelsius(40)))
        );
        assertThat(sensor).isEqualTo(expectedSensor);
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    public void should_use_default_limits_when_not_defined() {
        // Given
        doReturn(new DegreeCelsius(30)).when(temperatureCaptor).getTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new SensedAt(sensedAt)).when(sensedAtProvider).now();

        // When
        final Sensor sensor = getTemperatureUseCase.execute(new GetTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new SensedAt(sensedAt),
                new DegreeCelsius(30),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarnLimit(new DegreeCelsius(40)))
        );
        assertThat(sensor).isEqualTo(expectedSensor);
    }
}
