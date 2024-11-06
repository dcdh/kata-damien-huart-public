package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.spi.TakenAtProvider;
import com.harvest.katadamienhuart.domain.spi.TemperatureCaptor;
import com.harvest.katadamienhuart.domain.usecase.TestProvider;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
class AskForTemperatureEndpointTest extends AbstractInfrastructureTest {

    @InjectMock
    TemperatureCaptor temperatureCaptor;

    @InjectMock
    TakenAtProvider takenAtProvider;

    @Test
    void should_ask_for_temperature() {
        // Given
        doReturn(TestProvider.GIVEN_TEMPERATURE).when(temperatureCaptor).takeTemperature();
        doReturn(TestProvider.GIVEN_TAKEN_AT).when(takenAtProvider).now();

        // When & Then
        given()
                .accept("application/vnd.sensor-v1+json")
                .when()
                .get("/sensor/askForTemperature")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/vnd.sensor-v1+json")
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperature_asked.json"))
                .body("sensorState", is("WARM"))
                .body("takenTemperature", is(30))
                .body("takenAt", is("2021-10-01T00:00:00Z"));
    }

}
