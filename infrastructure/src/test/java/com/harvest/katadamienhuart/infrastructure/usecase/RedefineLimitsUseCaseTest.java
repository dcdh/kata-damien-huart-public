package com.harvest.katadamienhuart.infrastructure.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import com.harvest.katadamienhuart.domain.usecase.TestProvider;
import com.harvest.katadamienhuart.infrastructure.AbstractInfrastructureTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@QuarkusTest
class RedefineLimitsUseCaseTest extends AbstractInfrastructureTest {

    @Inject
    RedefineLimitsUseCase redefineLimitsUseCase;

    @Test
    void should_redefine_limits() {
        // Given

        // When
        final Limits limits = redefineLimitsUseCase.execute(TestProvider.GIVEN_REDEFINE_LIMITS_COMMAND);

        // Then
        assertAll(
                () -> assertThat(limits).isNotNull(),
                () -> {
                    try (final Connection connection = dataSource.getConnection();
                         final PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM T_LIMITS")) {
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        assertThat(resultSet.getLong("count")).isEqualTo(1L);
                    }
                }
        );
    }

}
