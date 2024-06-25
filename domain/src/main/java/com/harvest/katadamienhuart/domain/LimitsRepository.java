package com.harvest.katadamienhuart.domain;

import java.util.Optional;

public interface LimitsRepository {

    Optional<Limits> findLastLimits();

    Limits store(Limits limits);

}
