package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import com.harvest.katadamienhuart.infrastructure.postgres.PostgresSensorRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
public class SensorEndpointTest {
//    pour moi ces tests doivent dégager => ApplicationTest ...
//comment es je fais dans mon formula interpreter ??? non ! real-estate-ads-tracker !!!
    @InjectMock
    AskForTemperatureUseCase askForTemperatureUseCase;

    @InjectMock
    PostgresSensorRepository postgresSensorRepository;

    @Test
    public void should_take_temperature() {
        final ZonedDateTime takenAt = ZonedDateTime.now();
        doReturn(new Sensor(
                new TakenAt(takenAt),
                new TakenTemperature(new DegreeCelsius(22)),
                SensorState.WARM))
                .when(askForTemperatureUseCase).execute(any());

        given()
                .when()
                .get("/sensor/askForTemperature")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperature.json"));
    }

    @Test
    public void should_retrieve_last_15_temperatures() {
        final ZonedDateTime takenAt = ZonedDateTime.now();
        doReturn(List.of(
                new Sensor(
                        new TakenAt(takenAt),
                        new TakenTemperature(new DegreeCelsius(22)),
                        SensorState.WARM)))
                .when(postgresSensorRepository).getLast15OrderedByTakenAtDesc();

        given()
                .when()
                .get("/sensor/retrieveLast15Temperatures")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperatures.json"));
    }

}
