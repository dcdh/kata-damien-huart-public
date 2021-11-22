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
}
