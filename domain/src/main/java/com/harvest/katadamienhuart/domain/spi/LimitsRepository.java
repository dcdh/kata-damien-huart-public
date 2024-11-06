package com.harvest.katadamienhuart.domain.spi;

import com.harvest.katadamienhuart.domain.Limits;

import java.util.Optional;

public interface LimitsRepository {

    Optional<Limits> findLastLimits();

    Limits store(Limits limits);

}
