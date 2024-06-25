package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.WarmLimit;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsCommand;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import com.harvest.katadamienhuart.infrastructure.postgres.PostgresSensorRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class LimitsEndpointTest {
//    pour moi ces tests doivent dégager => ApplicationTest ...

//    ceci me choque !!!
    @InjectSpy
    RedefineLimitsUseCase redefineLimitsUseCase;

    @InjectMock
    PostgresSensorRepository postgresSensorRepository;

    @Test
    public void should_redefine_limits() {
        given()
                .param("newColdLimit", "20")
                .param("newWarmLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(204);
        verify(redefineLimitsUseCase, times(1)).execute(new RedefineLimitsCommand(
                new ColdLimit(new DegreeCelsius(20)), new WarmLimit(new DegreeCelsius(42))));
    }

    @Test
    public void should_return_expected_bad_request_response_when_cold_limit_is_not_positive() {
        given()
                .param("newColdLimit", "-20")
                .param("newWarmLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("-20°C is invalid. ColdLimit must be a positive temperature."));
    }

    @Test
    public void should_return_expected_bad_request_response_when_warm_limit_is_not_positive() {
        given()
                .param("newColdLimit", "20")
                .param("newWarmLimit", "-42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("-42°C is invalid. WarmLimit must be a positive temperature."));
    }

    @Test
    public void should_return_expected_bad_request_response_when_warm_limit_is_before_cold_limit() {
        given()
                .param("newColdLimit", "22")
                .param("newWarmLimit", "20")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("Warm limit (20°C) must be superior to cold limit (22°C)."));
    }

}
