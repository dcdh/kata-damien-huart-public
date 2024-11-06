package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.spi.TakenTemperatureRepository;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCaseTestResolver;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.LimitsEntity;
import com.harvest.katadamienhuart.infrastructure.persistence.postgres.TakenTemperatureEntity;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
@ExtendWith(RetrieveLast15TakenTemperaturesUseCaseTestResolver.class)
class RetrieveLast15TakenTemperaturesEndpointTest extends AbstractInfrastructureTest {

    @InjectSpy
    TakenTemperatureRepository takenTemperatureRepository;

    @RetrieveLast15TakenTemperaturesUseCaseTestResolver.ThreeTakenTemperaturesTest
    void should_retrieve_last_15_temperatures(final Limits limits,
                                              final RetrieveLast15TakenTemperaturesUseCaseTestResolver.TakenTemperatures givenTakenTemperatures,
                                              final RetrieveLast15TakenTemperaturesUseCaseTestResolver.SensorHistories expectedSensorHistories) {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            entityManager.persist(new LimitsEntity(limits));
            givenTakenTemperatures.takenTemperatures().forEach(takenTemperature ->
                    entityManager.persist(new TakenTemperatureEntity(takenTemperature)));
        });

        // When && Then
        given()
                .accept("application/vnd.sensor-history-v1+json")
                .when()
                .get("/sensor/retrieveLast15Temperatures")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/vnd.sensor-history-v1+json")
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperatures_history.json"))
                .body("size()", is(2))
                .body("[0].sensorState", is(expectedSensorHistories.sensorHistories().getFirst().sensorState().name()))
                .body("[0].takenTemperature", is(expectedSensorHistories.sensorHistories().getFirst().takenTemperature().temperature().degreeCelsius().temperature()))
                .body("[0].takenAt", is(expectedSensorHistories.sensorHistories().getFirst().takenTemperature().takenAt().at().format(FORMATTER_WITH_SECONDS)))
                .body("[1].sensorState", is(expectedSensorHistories.sensorHistories().get(1).sensorState().name()))
                .body("[1].takenTemperature", is(expectedSensorHistories.sensorHistories().get(1).takenTemperature().temperature().degreeCelsius().temperature()))
                .body("[1].takenAt", is(expectedSensorHistories.sensorHistories().get(1).takenTemperature().takenAt().at().format(FORMATTER_WITH_SECONDS)));
    }

    @Test
    public void should_handle_unknown_exception() {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(takenTemperatureRepository).findLast15OrderedByTakenAtDesc();

        // When && Then
        given()
                .accept("application/vnd.sensor-history-v1+json")
                .when()
                .get("/sensor/retrieveLast15Temperatures")
                .then()
                .log().all()
                .statusCode(500)
                .contentType("application/vnd.retrieve-last-15-taken-temperatures-error-v1+txt")
                .body(equalTo("Something wrong happened"));
        ;
    }
}
