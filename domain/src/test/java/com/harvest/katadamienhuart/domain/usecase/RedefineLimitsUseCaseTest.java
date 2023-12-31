package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedefineLimitsUseCaseTest {

    @Test
    public void should_redefine_limits() {
        // Given
        final LimitsRepository limitsRepository = mock(LimitsRepository.class);
        final RedefineLimitsUseCase redefineLimitsUseCase = new RedefineLimitsUseCase(limitsRepository);

        // When
        final Limits limits = redefineLimitsUseCase.execute(new RedefineLimitsCommand(
                new ColdLimit(new DegreeCelsius(10)),
                new WarmLimit(new DegreeCelsius(45))));

        // Then
        final Limits expectedLimits = new Limits(new ColdLimit(new DegreeCelsius(10)), new WarmLimit(new DegreeCelsius(45)));
        assertThat(limits).isEqualTo(expectedLimits);
        verify(limitsRepository, times(1)).store(expectedLimits);
    }

}
