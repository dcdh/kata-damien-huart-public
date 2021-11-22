package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.LimitsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
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
