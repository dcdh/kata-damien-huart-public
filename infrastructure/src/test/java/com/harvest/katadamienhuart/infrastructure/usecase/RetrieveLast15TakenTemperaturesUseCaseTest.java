package com.harvest.katadamienhuart.infrastructure.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.SensorHistory;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesException;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesRequest;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCase;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCaseTestResolver;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.LimitsEntity;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.TakenTemperatureEntity;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ExtendWith(RetrieveLast15TakenTemperaturesUseCaseTestResolver.class)
class RetrieveLast15TakenTemperaturesUseCaseTest extends AbstractInfrastructureTest {

    @Inject
    RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase;

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.ThreeTakenTemperaturesTest
    void should_return_last_15_taken_temperature(final Limits limits,
                                                 final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures,
                                                 final RetrieveLast15TakenTemperaturesUseCaseTestResolver.SensorHistories expectedSensorHistories)
            throws RetrieveLast15TakenTemperaturesException {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            entityManager.persist(new LimitsEntity(limits));
            givenTakenTemperatures.takenTemperatures().forEach(takenTemperature ->
                    entityManager.persist(new TakenTemperatureEntity(takenTemperature)));
        });

        // When
        final List<SensorHistory> sensorHistories = retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest());

        // Then
        assertThat(sensorHistories).containsAll(expectedSensorHistories.sensorHistories());
    }
}
