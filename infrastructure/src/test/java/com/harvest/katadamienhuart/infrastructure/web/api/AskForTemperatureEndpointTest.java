package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.TakenAt;
import com.harvest.katadamienhuart.domain.Temperature;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import com.harvest.katadamienhuart.domain.spi.TakenAtProvider;
import com.harvest.katadamienhuart.domain.spi.TemperatureCaptor;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCaseTestResolver;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
@ExtendWith(AskForTemperatureUseCaseTestResolver.class)
class AskForTemperatureEndpointTest extends AbstractInfrastructureTest {

    @InjectMock
    TemperatureCaptor temperatureCaptor;

    @InjectMock
    TakenAtProvider takenAtProvider;

    @InjectMock
    LimitsRepository limitsRepository;

    @Test
    void should_ask_for_temperature(final Temperature temperature, final TakenAt takenAt, final Sensor expectedSensor) {
        // Given
        doReturn(temperature).when(temperatureCaptor).takeTemperature();
        doReturn(takenAt).when(takenAtProvider).now();

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
                .body("sensorState", is(expectedSensor.sensorState().name()))
                .body("takenTemperature", is(expectedSensor.takenTemperature().temperature().degreeCelsius().temperature()))
                .body("takenAt", is(expectedSensor.takenTemperature().takenAt().at().format(FORMATTER_WITH_SECONDS)))
        ;
    }

    @Test
    public void should_handle_unknown_exception() {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(limitsRepository).findLastLimits();

        // When & Then
        given()
                .accept("application/vnd.sensor-v1+json")
                .when()
                .get("/sensor/askForTemperature")
                .then()
                .log().all()
                .statusCode(500)
                .contentType("application/vnd.ask-for-temperature-error-v1+txt")
                .body(equalTo("Something wrong happened"));
        ;
    }
}
