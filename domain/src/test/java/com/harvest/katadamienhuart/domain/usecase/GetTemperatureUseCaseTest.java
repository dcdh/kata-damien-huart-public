package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetTemperatureUseCaseTest {

    @Test
    public void should_get_temperature() {
        // Given
        final TemperatureCaptor temperatureCaptor = mock(TemperatureCaptor.class);
        final SensedAtProvider sensedAtProvider = mock(SensedAtProvider.class);
        final SensorRepository sensorRepository = mock(SensorRepository.class);
        final ThresholdRepository thresholdRepository = mock(ThresholdRepository.class);
        doReturn(new DegreeCelsius(30)).when(temperatureCaptor).getTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new SensedAt(sensedAt)).when(sensedAtProvider).now();
        doReturn(new Thresholds(
                new ColdThreshold(new DegreeCelsius(22)),
                new WarnThreshold(new DegreeCelsius(40))
        )).when(thresholdRepository).getThresholds();

        final GetTemperatureUseCase getTemperatureUseCase = new GetTemperatureUseCase(
                temperatureCaptor, sensedAtProvider, sensorRepository, thresholdRepository);

        // When
        final Sensor sensor = getTemperatureUseCase.execute(new GetTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new SensedAt(sensedAt),
                new DegreeCelsius(30),
                new Thresholds(
                        new ColdThreshold(new DegreeCelsius(22)),
                        new WarnThreshold(new DegreeCelsius(40)))
        );
        assertThat(sensor).isEqualTo(expectedSensor);
        verify(sensorRepository, times(1)).save(sensor);
    }

}
