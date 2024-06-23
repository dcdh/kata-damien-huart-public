package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostgresSensorRepository implements SensorRepository {

    private final EntityManager entityManager;

    public PostgresSensorRepository(final EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    @Override
    @Transactional
    public void save(final Sensor sensor) {
        entityManager.persist(new SensorEntity(sensor));
    }

    public List<Sensor> getLast15OrderedBySensedAtDesc() {
        return entityManager.createQuery("SELECT s FROM SensorEntity s ORDER BY sensedAt DESC", SensorEntity.class)
                .setMaxResults(15)
                .getResultList()
                .stream()
                .map(SensorEntity::toSensor)
                .collect(Collectors.toList());
    }

}
