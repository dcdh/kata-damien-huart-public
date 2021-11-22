package com.harvest.katadamienhuart.infrastructure;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class E2ETest {

    @Test
    @Order(1)
    public void should_get_temperature() {
        given()
                .when()
                .get("/sensor")
                .then()
                .log().all()
                .statusCode(200)
                .body("sensorState", equalTo("COLD"))
                .body("sensedTemperature", equalTo(10));
    }

    @Test
    @Order(2)
    public void should_redefine_limits() {
        given()
                .param("newColdLimit", "20")
                .param("newWarnLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    @Order(3)
    public void should_get_last_15_temperatures() {
        given()
                .when()
                .get("/sensor/last15Temperatures")
                .then()
                .log().all()
                .statusCode(200);
    }

}
