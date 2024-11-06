package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimitMustBePositiveException;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimitMustBePositiveException;
import com.harvest.katadamienhuart.domain.WarmLimitMustBeSuperiorToColdLimitException;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RedefineLimitsUseCaseTestResolver.class)
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

    @RedefineLimitsUseCaseTestResolver.RedefineLimitsTest
    void should_redefine_limits(final RedefineLimitsRequest givenRedefineLimitsRequest,
                                final Limits expectedLimits) throws RedefineLimitsException {
        // Given

        // When
        final Limits limits = redefineLimitsUseCase.execute(givenRedefineLimitsRequest);

        // Then
        assertAll(
                () -> assertThat(limits).isEqualTo(expectedLimits),
                () -> verify(limitsRepository, times(1)).store(expectedLimits)
        );
    }

    @RedefineLimitsUseCaseTestResolver.ColdLimitIsNegativeTest
    void should_fail_when_cold_limit_is_negative(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        // Given

        // When & Then
        assertAll(
                () -> assertThatThrownBy(() -> redefineLimitsUseCase.execute(givenRedefineLimitsRequest))
                        .isInstanceOf(RedefineLimitsException.class)
                        .hasRootCauseInstanceOf(ColdLimitMustBePositiveException.class),
                () -> verify(limitsRepository, times(0)).store(any())
        );
    }

    @RedefineLimitsUseCaseTestResolver.WarmLimitIsNegativeTest
    void should_fail_when_warm_limit_is_negative(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        // Given

        // When & Then
        assertAll(
                () -> assertThatThrownBy(() -> redefineLimitsUseCase.execute(givenRedefineLimitsRequest))
                        .isInstanceOf(RedefineLimitsException.class)
                        .hasRootCauseInstanceOf(WarmLimitMustBePositiveException.class),
                () -> verify(limitsRepository, times(0)).store(any())
        );
    }

    @RedefineLimitsUseCaseTestResolver.WarmLimitIsBeforeColdLimitTest
    void should_fail_when_warm_limit_is_before_cold_limit(final RedefineLimitsRequest givenRedefineLimitsRequest) {
        // Given

        // When & Then
        assertAll(
                () -> assertThatThrownBy(() -> redefineLimitsUseCase.execute(givenRedefineLimitsRequest))
                        .isInstanceOf(RedefineLimitsException.class)
                        .hasRootCauseInstanceOf(WarmLimitMustBeSuperiorToColdLimitException.class),
                () -> verify(limitsRepository, times(0)).store(any())
        );
    }

    @RedefineLimitsUseCaseTestResolver.RedefineLimitsTest
    public void should_handle_unknown_exception(final RedefineLimitsRequest givenRedefineLimitsRequest,
                                                final Limits expectedLimits) {
        // Given
        doThrow(new IllegalStateException("Unknown exception happened")).when(limitsRepository).store(expectedLimits);

        // When
        assertAll(
                () ->
                        assertThatThrownBy(() -> redefineLimitsUseCase.execute(givenRedefineLimitsRequest))
                                .isInstanceOf(RedefineLimitsException.class)
                                .hasRootCauseInstanceOf(IllegalStateException.class)
                                .hasRootCauseMessage("Unknown exception happened"),
                () -> verify(limitsRepository, times(1)).store(expectedLimits)
        );
    }
}
