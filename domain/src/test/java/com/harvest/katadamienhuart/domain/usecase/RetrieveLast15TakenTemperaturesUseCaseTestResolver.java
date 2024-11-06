package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public final class RetrieveLast15TakenTemperaturesUseCaseTestResolver implements ParameterResolver {

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("THREE_TAKEN_TEMPERATURES")
    @Test
    public @interface ThreeTakenTemperaturesTest {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("TWENTY_TAKEN_TEMPERATURES")
    @Test
    public @interface TwentyTakenTemperaturesTest {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("UNORDERED_TAKEN_TEMPERATURES")
    @Test
    public @interface UnorderedTakenTemperaturesTest {
    }

    public enum State {
        THREE_TAKEN_TEMPERATURES, TWENTY_TAKEN_TEMPERATURES, UNORDERED_TAKEN_TEMPERATURES;

        public static boolean matchTag(final String tag) {
            for (final State state : State.values()) {
                if (state.name().equals(tag)) {
                    return true;
                }
            }
            return false;
        }
    }

    private final Map<State, List<TakenTemperature>> TAKEN_TEMPERATURES_BY_STATE = Map.of(
            State.THREE_TAKEN_TEMPERATURES, IntStream.range(1, 3).boxed()
                    .sorted(Collections.reverseOrder())
                    .map(dayOfMonth ->
                            new TakenTemperature(
                                    new Temperature(new DegreeCelsius(20)),
                                    new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
                    )
                    .toList(),
            State.TWENTY_TAKEN_TEMPERATURES, IntStream.range(1, 20).boxed()
                    .sorted(Collections.reverseOrder())
                    .map(dayOfMonth ->
                            new TakenTemperature(
                                    new Temperature(new DegreeCelsius(20)),
                                    new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
                    )
                    .toList(),
            State.UNORDERED_TAKEN_TEMPERATURES, IntStream.range(1, 10).boxed()
                    .map(dayOfMonth ->
                            new TakenTemperature(
                                    new Temperature(new DegreeCelsius(20)),
                                    new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
                    )
                    .toList()
    );

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return List.of(Limits.class, TakenTemperatures.class, SensorHistories.class).contains(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        final State state = extensionContext.getTags()
                .stream()
                .filter(State::matchTag)
                .map(State::valueOf)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Should not be here: unknown tag"));
        final Limits limits = new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(40))
        );
        if (Limits.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            return limits;
        } else if (TakenTemperatures.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            final List<TakenTemperature> takenTemperatures = TAKEN_TEMPERATURES_BY_STATE.get(state);
            return new TakenTemperatures(takenTemperatures);
        } else if (SensorHistories.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            return new SensorHistories(List.of(
                    new SensorHistory(new TakenTemperature(
                            new Temperature(new DegreeCelsius(20)),
                            new TakenAt(ZonedDateTime.of(2021, 10, 2, 0, 0, 0, 0, ZoneOffset.UTC))),
                            SensorState.COLD),
                    new SensorHistory(new TakenTemperature(
                            new Temperature(new DegreeCelsius(20)),
                            new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                            SensorState.COLD)
            ));
        } else {
            throw new IllegalStateException("Should not be here: unsupported parameter type");
        }
    }

    public record TakenTemperatures(List<TakenTemperature> takenTemperatures) implements Serializable {
        public TakenTemperatures {
            Objects.requireNonNull(takenTemperatures);
        }
    }

    public record SensorHistories(List<SensorHistory> sensorHistories) implements Serializable {
        public SensorHistories {
            Objects.requireNonNull(sensorHistories);
        }
    }
}
