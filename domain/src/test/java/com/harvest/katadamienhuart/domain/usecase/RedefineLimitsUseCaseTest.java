package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
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

    @Test
    public void should_redefine_limits() {
        // Given
        final RedefineLimitsUseCase redefineLimitsUseCase = new RedefineLimitsUseCase(limitsRepository);

        // When
        final Limits limits = redefineLimitsUseCase.execute(new RedefineLimitsCommand(
                new ColdLimit(new DegreeCelsius(10)),
                new WarmLimit(new DegreeCelsius(45))));

        // Then
        final Limits expectedLimits = new Limits(new ColdLimit(new DegreeCelsius(10)), new WarmLimit(new DegreeCelsius(45)));
        assertAll(
                () -> assertThat(limits).isEqualTo(expectedLimits),
                () -> verify(limitsRepository, times(1)).store(expectedLimits)
        );
    }
}
