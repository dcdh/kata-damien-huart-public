package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class PostgresLimitsRepositoryTest extends RepositoryTest {

    private static final String COUNT_LIMITS_SQL = "SELECT COUNT(*) FROM public.T_LIMITS";

    @Inject
    PostgresLimitsRepository postgresLimitsRepository;

    @Test
    public void should_return_null_when_limits_have_not_been_defined() {
        assertThat(postgresLimitsRepository.getLimits()).isNull();
    }

    @Test
    public void should_store_limits() {
        // Given

        // When
        postgresLimitsRepository.store(new Limits(
                new ColdLimit(new DegreeCelsius(20)),
                new WarmLimit(new DegreeCelsius(40))));

        // Then
        assertThat(((Number) entityManager.createNativeQuery(COUNT_LIMITS_SQL).getSingleResult()).longValue()).isEqualTo(1l);
    }

    @Test
    public void should_get_last_limits() throws Exception {
        // Given
        runInTransaction(() -> {
            entityManager.persist(new LimitsEntity(new Limits(
                    new ColdLimit(new DegreeCelsius(20)),
                    new WarmLimit(new DegreeCelsius(40)))));
            entityManager.persist(new LimitsEntity(new Limits(
                    new ColdLimit(new DegreeCelsius(22)),
                    new WarmLimit(new DegreeCelsius(42)))));
            return null;
        });


        // When
        final Limits limits = postgresLimitsRepository.getLimits();

        // Then
        assertThat(limits).isEqualTo(new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(42))));
    }

}
