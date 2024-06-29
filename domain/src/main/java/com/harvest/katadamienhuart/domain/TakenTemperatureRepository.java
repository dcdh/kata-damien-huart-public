package com.harvest.katadamienhuart.domain;

import java.util.List;

public interface TakenTemperatureRepository {

    void store(TakenTemperature takenTemperature);

    List<TakenTemperature> findLast15OrderedByTakenAtDesc();
}
