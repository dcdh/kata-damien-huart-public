package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.LimitsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class PostgresLimitsRepository implements LimitsRepository {

    @Override
    public Optional<Limits> findLastLimits() {
        return Optional.ofNullable(LimitsEntity.findFirstOrderByIdDesc())
                .map(LimitsEntity::toLimits);
    }

    @Override
    @Transactional
    public Limits store(final Limits limits) {
        LimitsEntity.persist(new LimitsEntity(limits));
        return limits;
    }
}
