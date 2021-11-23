package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.WarnLimit;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsCommand;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class LimitsEndpointTest {

    @InjectMock
    RedefineLimitsUseCase redefineLimitsUseCase;

    @Test
    public void should_redefine_limits() {
        given()
                .param("newColdLimit", "20")
                .param("newWarnLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(204);
        verify(redefineLimitsUseCase, times(1)).execute(new RedefineLimitsCommand(
                new ColdLimit(new DegreeCelsius(20)), new WarnLimit(new DegreeCelsius(42))));
    }

    @Test
    public void should_return_expected_bad_request_response_when_cold_limit_is_not_positive() {
        given()
                .param("newColdLimit", "-20")
                .param("newWarnLimit", "42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("-20°C is invalid. ColdLimit must be a positive temperature."));
    }

    @Test
    public void should_return_expected_bad_request_response_when_warn_limit_is_not_positive() {
        given()
                .param("newColdLimit", "20")
                .param("newWarnLimit", "-42")
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("-42°C is invalid. WarnLimit must be a positive temperature."));
    }

}
