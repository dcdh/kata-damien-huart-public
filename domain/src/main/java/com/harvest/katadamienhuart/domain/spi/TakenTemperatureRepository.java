package com.harvest.katadamienhuart.domain.spi;

import com.harvest.katadamienhuart.domain.TakenTemperature;

import java.util.List;

public interface TakenTemperatureRepository {

    void store(TakenTemperature takenTemperature);

    List<TakenTemperature> findLast15OrderedByTakenAtDesc();
}
