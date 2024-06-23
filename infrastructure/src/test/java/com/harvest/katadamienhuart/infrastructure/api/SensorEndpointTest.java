package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.usecase.TakeTemperatureUseCase;
import com.harvest.katadamienhuart.infrastructure.postgres.PostgresSensorRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SensorEndpointTest {

    @InjectMock
    private TakeTemperatureUseCase takeTemperatureUseCase;

    @InjectMock
    private PostgresSensorRepository postgresSensorRepository;

    @Test
    public void should_get_temperature() {
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new Sensor(
                new TakenAt(sensedAt),
                new TakenTemperature(new DegreeCelsius(22)),
                SensorState.WARM))
                .when(takeTemperatureUseCase).execute(any());

        given()
                .when()
                .get("/sensor/takeTemperature")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperature.json"));
    }

    @Test
    public void should_get_last_15_temperatures() {
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(List.of(
                new Sensor(
                        new TakenAt(sensedAt),
                        new TakenTemperature(new DegreeCelsius(22)),
                        SensorState.WARM)))
                .when(postgresSensorRepository).getLast15OrderedBySensedAtDesc();

        given()
                .when()
                .get("/sensor/last15Temperatures")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expected/temperatures.json"));
    }

}
