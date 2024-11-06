package com.harvest.katadamienhuart.infrastructure.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsRequest;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCaseTestResolver;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@QuarkusTest
@ExtendWith(RedefineLimitsUseCaseTestResolver.class)
class RedefineLimitsUseCaseTest extends AbstractInfrastructureTest {

    @Inject
    RedefineLimitsUseCase redefineLimitsUseCase;

    @RedefineLimitsUseCaseTestResolver.RedefineLimitsTest
    void should_redefine_limits(final RedefineLimitsRequest givenRedefineLimitsRequest,
                                final Limits expectedLimits) throws RedefineLimitsException {
        // Given

        // When
        final Limits limits = redefineLimitsUseCase.execute(givenRedefineLimitsRequest);

        // Then
        assertAll(
                () -> assertThat(limits).isNotNull(),
                () -> {
                    try (final Connection connection = dataSource.getConnection();
                         final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM T_LIMITS")) {
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        assertAll(
                                () -> assertThat(resultSet.getInt("coldlimit")).isEqualTo(expectedLimits.coldLimit().limit().temperature()),
                                () -> assertThat(resultSet.getInt("warmlimit")).isEqualTo(expectedLimits.warmLimit().limit().temperature())
                        );
                    }
                }
        );
    }
}
