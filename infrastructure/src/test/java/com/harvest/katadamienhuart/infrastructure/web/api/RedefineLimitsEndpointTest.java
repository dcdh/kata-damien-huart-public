package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsRequest;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCaseTestResolver;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
@ExtendWith(RedefineLimitsUseCaseTestResolver.class)
class RedefineLimitsEndpointTest extends AbstractInfrastructureTest {

    @InjectSpy
    LimitsRepository limitsRepository;

    @RedefineLimitsUseCaseTestResolver.RedefineLimitsTest
    void should_redefine_limits(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        given()
                .param("newColdLimit", givenRedefineLimitsRequest.newColdLimit().limit().temperature())
                .param("newWarmLimit", givenRedefineLimitsRequest.newWarmLimit().limit().temperature())
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(204);
    }

    @RedefineLimitsUseCaseTestResolver.ColdLimitIsNegativeTest
    void should_return_expected_bad_request_response_when_cold_limit_is_not_positive(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        given()
                .param("newColdLimit", givenRedefineLimitsRequest.newColdLimit().limit().temperature())
                .param("newWarmLimit", givenRedefineLimitsRequest.newWarmLimit().limit().temperature())
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("-20°C is invalid. ColdLimit must be a positive temperature."));
    }

    @RedefineLimitsUseCaseTestResolver.WarmLimitIsNegativeTest
    void should_return_expected_bad_request_response_when_warm_limit_is_not_positive(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        given()
                .param("newColdLimit", givenRedefineLimitsRequest.newColdLimit().limit().temperature())
                .param("newWarmLimit", givenRedefineLimitsRequest.newWarmLimit().limit().temperature())
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("-42°C is invalid. WarmLimit must be a positive temperature."));
    }

    @RedefineLimitsUseCaseTestResolver.WarmLimitIsBeforeColdLimitTest
    void should_return_expected_bad_request_response_when_warm_limit_is_before_cold_limit(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        given()
                .param("newColdLimit", givenRedefineLimitsRequest.newColdLimit().limit().temperature())
                .param("newWarmLimit", givenRedefineLimitsRequest.newWarmLimit().limit().temperature())
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(400)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("Warm limit (20°C) must be superior to cold limit (22°C)."));
    }

    @RedefineLimitsUseCaseTestResolver.RedefineLimitsTest
    public void should_handle_unknown_exception(final RedefineLimitsRequest givenRedefineLimitsRequest,
                                                final Limits expectedLimits) {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(limitsRepository).store(expectedLimits);

        // When && Then
        given()
                .param("newColdLimit", givenRedefineLimitsRequest.newColdLimit().limit().temperature())
                .param("newWarmLimit", givenRedefineLimitsRequest.newWarmLimit().limit().temperature())
                .when()
                .post("/limits/redefineLimits")
                .then()
                .log().all()
                .statusCode(500)
                .contentType("application/vnd.redefine-limit-error-v1+txt")
                .body(equalTo("Something wrong happened"));
    }
}
