package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.LimitsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import java.util.Objects;

@ApplicationScoped
public class PostgresLimitsRepository implements LimitsRepository {

    private final EntityManager entityManager;

    public PostgresLimitsRepository(final EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    @Override
    public Limits getLimits() {
        try {
            return entityManager
                    .createQuery("SELECT l FROM LimitsEntity l ORDER BY id DESC", LimitsEntity.class)
                    .setMaxResults(1)
                    .getSingleResult().toDomain();
        } catch (final NoResultException noResultException) {
            return null;
        }
    }

    @Override
    @Transactional
    public void store(final Limits limits) {
        entityManager.persist(new LimitsEntity(limits));
    }
}
