package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.usecase.TestProvider;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import com.harvest.katadamienhuart.infrastructure.postgres.LimitsEntity;
import com.harvest.katadamienhuart.infrastructure.postgres.TakenTemperatureEntity;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RetrieveLast15TakenTemperaturesEndpointTest extends AbstractInfrastructureTest {

    @Test
    void should_retrieve_last_15_temperatures() {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            entityManager.persist(new LimitsEntity(TestProvider.GIVEN_LIMITS));
            TestProvider.GIVEN_TAKEN_TEMPERATURES.forEach(takenTemperature ->
                    entityManager.persist(new TakenTemperatureEntity(takenTemperature)));
        });

        given()
                .when()
                .get("/sensor/retrieveLast15Temperatures")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperatures_history.json"))
                .body("size()", is(2))
                .body("[0].sensorState", is("COLD"))
                .body("[0].takenTemperature", is(20))
                .body("[0].takenAt", is("2021-10-02T00:00:00Z"))
                .body("[1].sensorState", is("COLD"))
                .body("[1].takenTemperature", is(20))
                .body("[1].takenAt", is("2021-10-01T00:00:00Z"));
    }

}
