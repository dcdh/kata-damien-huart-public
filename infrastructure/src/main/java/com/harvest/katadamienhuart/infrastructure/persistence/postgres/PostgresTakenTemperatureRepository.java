package com.harvest.katadamienhuart.infrastructure.persistence.postgres;

import com.harvest.katadamienhuart.domain.TakenTemperature;
import com.harvest.katadamienhuart.domain.spi.TakenTemperatureRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PostgresTakenTemperatureRepository implements TakenTemperatureRepository {

    @Override
    @Transactional
    public void store(final TakenTemperature takenTemperature) {
        TakenTemperatureEntity.persist(new TakenTemperatureEntity(takenTemperature));
    }

    public List<TakenTemperature> findLast15OrderedByTakenAtDesc() {
        return TakenTemperatureEntity.findLast15OrderByTakenAtDesc()
                .map(TakenTemperatureEntity::toTakenTemperature)
                .toList();
    }

}
