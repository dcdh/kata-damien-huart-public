package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RedefineLimitsUseCaseTest {

    @Spy
    DefaultLimitsRepository limitsRepository;

    // Need to do it this way because we return the limits from the repository after storing
    public static class DefaultLimitsRepository implements LimitsRepository {

        @Override
        public Optional<Limits> findLastLimits() {
            throw new IllegalStateException("Should not be called");
        }

        @Override
        public Limits store(final Limits limits) {
            return limits;
        }
    }

    private RedefineLimitsUseCase redefineLimitsUseCase;

    @BeforeEach
    void setup() {
        this.redefineLimitsUseCase = new RedefineLimitsUseCase(limitsRepository);
    }

    @Test
    void should_redefine_limits() throws RedefineLimitsException {
        // Given

        // When
        final Limits limits = redefineLimitsUseCase.execute(TestProvider.GIVEN_REDEFINE_LIMITS_REQUEST);

        // Then
        final Limits expectedLimits = new Limits(new ColdLimit(new DegreeCelsius(10)), new WarmLimit(new DegreeCelsius(45)));
        assertAll(
                () -> assertThat(limits).isEqualTo(expectedLimits),
                () -> verify(limitsRepository, times(1)).store(expectedLimits)
        );
    }
}
