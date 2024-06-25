package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostgresSensorRepository implements SensorRepository {

    @Override
    @Transactional
    public Sensor store(final Sensor sensor) {
        SensorEntity.persist(new SensorEntity(sensor));
        return sensor;
    }

    public List<Sensor> getLast15OrderedByTakenAtDesc() {
        return SensorEntity.getLast15OrderByTakenAtDesc()
                .map(SensorEntity::toSensor)
                .collect(Collectors.toList());
    }

}
