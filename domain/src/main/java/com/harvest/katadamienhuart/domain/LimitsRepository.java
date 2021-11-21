package com.harvest.katadamienhuart.domain;

public interface LimitsRepository {

    Limits getLimits();

    void store(Limits limits);

}
