package com.harvest.katadamienhuart.infrastructure.usecase;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.TakenAtProvider;
import com.harvest.katadamienhuart.domain.TemperatureCaptor;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureRequest;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import com.harvest.katadamienhuart.domain.usecase.TestProvider;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
class AskForTemperatureUseCaseTest extends AbstractInfrastructureTest {

    @InjectMock
    TemperatureCaptor temperatureCaptor;

    @InjectMock
    TakenAtProvider takenAtProvider;

    @Inject
    AskForTemperatureUseCase askForTemperatureUseCase;

    @Test
    void should_ask_for_temperature() {
        // Given
        doReturn(TestProvider.GIVEN_TEMPERATURE).when(temperatureCaptor).takeTemperature();
        doReturn(TestProvider.GIVEN_TAKEN_AT).when(takenAtProvider).now();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureRequest());

        // Then
        assertAll(
                () -> assertThat(sensor).isNotNull(),
                () -> {
                    try (final Connection connection = dataSource.getConnection();
                         final PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM T_TAKEN_TEMPERATURE")) {
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        assertThat(resultSet.getLong("count")).isEqualTo(1L);
                    }
                }
        );
    }
}
