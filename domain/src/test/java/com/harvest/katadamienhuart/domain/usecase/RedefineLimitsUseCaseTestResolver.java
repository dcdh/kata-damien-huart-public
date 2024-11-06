package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

public final class RedefineLimitsUseCaseTestResolver implements ParameterResolver {

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("REDEFINE_LIMITS")
    @Test
    public @interface RedefineLimitsTest {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("COLD_LIMIT_IS_NEGATIVE")
    @Test
    public @interface ColdLimitIsNegativeTest {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("WARM_LIMIT_IS_NEGATIVE")
    @Test
    public @interface WarmLimitIsNegativeTest {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("WARM_LIMIT_IS_BEFORE_COLD_LIMIT")
    @Test
    public @interface WarmLimitIsBeforeColdLimitTest {
    }

    public enum State {
        REDEFINE_LIMITS, COLD_LIMIT_IS_NEGATIVE, WARM_LIMIT_IS_NEGATIVE, WARM_LIMIT_IS_BEFORE_COLD_LIMIT;

        public static boolean matchTag(final String tag) {
            for (final State state : State.values()) {
                if (state.name().equals(tag)) {
                    return true;
                }
            }
            return false;
        }
    }

    private final Map<State, RedefineLimitsRequest> REDEFINE_LIMITS_REQUEST_BY_STATE = Map.of(
            State.REDEFINE_LIMITS, new RedefineLimitsRequest(
                    new NewColdLimit(new DegreeCelsius(20)),
                    new NewWarmLimit(new DegreeCelsius(42))),
            State.COLD_LIMIT_IS_NEGATIVE, new RedefineLimitsRequest(
                    new NewColdLimit(new DegreeCelsius(-20)),
                    new NewWarmLimit(new DegreeCelsius(42))),
            State.WARM_LIMIT_IS_NEGATIVE, new RedefineLimitsRequest(
                    new NewColdLimit(new DegreeCelsius(20)),
                    new NewWarmLimit(new DegreeCelsius(-42))),
            State.WARM_LIMIT_IS_BEFORE_COLD_LIMIT, new RedefineLimitsRequest(
                    new NewColdLimit(new DegreeCelsius(22)),
                    new NewWarmLimit(new DegreeCelsius(20)))
    );

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return List.of(RedefineLimitsRequest.class, Limits.class).contains(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {

        if (RedefineLimitsRequest.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            final State state = extensionContext.getTags()
                    .stream()
                    .filter(State::matchTag)
                    .map(State::valueOf)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Should not be here: unknown tag"));
            return REDEFINE_LIMITS_REQUEST_BY_STATE.get(state);
        } else if (Limits.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            return new Limits(new ColdLimit(new DegreeCelsius(20)), new WarmLimit(new DegreeCelsius(42)));
        } else {
            throw new IllegalStateException("Should not be here: unsupported parameter type");
        }
    }
}
