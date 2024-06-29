package com.harvest.katadamienhuart.infrastructure;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Should be done in api package
// Should be removed
@Deprecated
class ApplicationTest {

    @Test
    @Order(1)
    void should_get_temperature() {
        given()
                .accept("application/vnd.sensor-v1+json")
                .when()
                .get("/sensor/askForTemperature")
                .then()
                .log().all()
                .statusCode(200)
                .body("sensorState", equalTo("COLD"))
                .body("takenTemperature", equalTo(10));
    }

    @Test
    @Order(2)
    void should_redefine_limits() {
        given()
                .param("newColdLimit", "20")
                .param("newWarmLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    @Order(3)
    void should_get_last_15_temperatures() {
        given()
                .accept("application/vnd.sensor-history-v1+json")
                .when()
                .get("/sensor/retrieveLast15Temperatures")
                .then()
                .log().all()
                .statusCode(200);
    }

}
