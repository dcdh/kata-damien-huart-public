package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class RedefineLimitsEndpointTest extends AbstractInfrastructureTest {

    @Test
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
    void should_return_expected_bad_request_response_when_cold_limit_is_not_positive() {
        given()
                .param("newColdLimit", "-20")
                .param("newWarmLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("-20°C is invalid. ColdLimit must be a positive temperature."));
    }

    @Test
    void should_return_expected_bad_request_response_when_warm_limit_is_not_positive() {
        given()
                .param("newColdLimit", "20")
                .param("newWarmLimit", "-42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("-42°C is invalid. WarmLimit must be a positive temperature."));
    }

    @Test
    void should_return_expected_bad_request_response_when_warm_limit_is_before_cold_limit() {
        given()
                .param("newColdLimit", "22")
                .param("newWarmLimit", "20")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("Warm limit (20°C) must be superior to cold limit (22°C)."));
    }
}
