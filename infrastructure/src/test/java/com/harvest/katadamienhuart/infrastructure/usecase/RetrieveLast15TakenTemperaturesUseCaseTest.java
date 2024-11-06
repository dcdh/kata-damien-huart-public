package com.harvest.katadamienhuart.infrastructure.usecase;

import com.harvest.katadamienhuart.domain.SensorHistory;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesException;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesRequest;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCase;
import com.harvest.katadamienhuart.domain.usecase.TestProvider;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.LimitsEntity;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.TakenTemperatureEntity;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class RetrieveLast15TakenTemperaturesUseCaseTest extends AbstractInfrastructureTest {

    @Inject
    RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase;

    @Test
    void should_return_last_15_taken_temperature() throws RetrieveLast15TakenTemperaturesException {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            entityManager.persist(new LimitsEntity(TestProvider.GIVEN_LIMITS));
            TestProvider.GIVEN_TAKEN_TEMPERATURES.forEach(takenTemperature ->
                    entityManager.persist(new TakenTemperatureEntity(takenTemperature)));
        });

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertThat(sensorHistories).hasSize(2);
    }
}
