package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class RetrieveLast15TakenTemperaturesUseCase implements UseCase<RetrieveLast15TakenTemperaturesCommand, List<SensorHistory>> {

    private static final Function<List<TakenTemperature>, Void> SORTED_DESC_FAIL_FAST = new IsSortedDesc<TakenTemperature>()
            .andThen(new FailOnFalse("Taken temperature history is not sorted descending"));

    private final LimitsRepository limitsRepository;
    private final TakenTemperatureRepository takenTemperatureRepository;

    public RetrieveLast15TakenTemperaturesUseCase(final LimitsRepository limitsRepository,
                                                  final TakenTemperatureRepository takenTemperatureRepository) {
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
        this.takenTemperatureRepository = Objects.requireNonNull(takenTemperatureRepository);
    }

    @Override
    public List<SensorHistory> execute(final RetrieveLast15TakenTemperaturesCommand command) {
        final Limits lastLimits = limitsRepository.findLastLimits().orElseGet(Limits::ofDefault);
        final List<TakenTemperature> last15OrderedByTakenAtDesc = takenTemperatureRepository.findLast15OrderedByTakenAtDesc();
        Validate.validState(last15OrderedByTakenAtDesc.size() <= 15, "Max 15 taken temperature expected");
        SORTED_DESC_FAIL_FAST.apply(last15OrderedByTakenAtDesc);
        new IsSortedDesc<TakenTemperature>().apply(last15OrderedByTakenAtDesc);
        return last15OrderedByTakenAtDesc
                .stream()
                .map(takenTemperature -> new SensorHistory(
                        takenTemperature,
                        SensorState.fromSensedTemperature(takenTemperature, lastLimits)))
                .toList();
    }

}
