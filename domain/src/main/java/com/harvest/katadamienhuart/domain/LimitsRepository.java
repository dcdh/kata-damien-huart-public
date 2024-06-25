package com.harvest.katadamienhuart.domain;

public interface LimitsRepository {

    Limits getLastLimits();

    Limits store(Limits limits);

}
