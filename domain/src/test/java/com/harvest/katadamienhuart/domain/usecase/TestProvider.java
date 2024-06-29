package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TestProvider {
    public static Temperature GIVEN_TEMPERATURE = new Temperature(new DegreeCelsius(30));

    public static TakenAt GIVEN_TAKEN_AT = new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC));

    public static Limits GIVEN_LIMITS = new Limits(
            new ColdLimit(new DegreeCelsius(22)),
            new WarmLimit(new DegreeCelsius(40))
    );

    public static RedefineLimitsCommand GIVEN_REDEFINE_LIMITS_COMMAND = new RedefineLimitsCommand(
            new ColdLimit(new DegreeCelsius(10)),
            new WarmLimit(new DegreeCelsius(45)));

    public static List<TakenTemperature> GIVEN_TAKEN_TEMPERATURES = IntStream.range(1, 3).boxed()
            .sorted(Collections.reverseOrder())
            .map(dayOfMonth ->
                    new TakenTemperature(
                            new Temperature(new DegreeCelsius(20)),
                            new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)))
            )
            .toList();

}
