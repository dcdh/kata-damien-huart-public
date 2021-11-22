package com.harvest.katadamienhuart.infrastructure.postgres;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.concurrent.Callable;

public abstract class RepositoryTest {

    public static final String TRUNCATE_T_LIMITS = "TRUNCATE TABLE public.T_LIMITS";
    public static final String TRUNCATE_T_SENSOR = "TRUNCATE TABLE public.T_SENSOR";

    @Inject
    UserTransaction userTransaction;

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @AfterEach
    public void flush() throws Exception {
        runInTransaction(() ->
                entityManager.createNativeQuery(TRUNCATE_T_LIMITS).executeUpdate());
        runInTransaction(() ->
                entityManager.createNativeQuery(TRUNCATE_T_SENSOR).executeUpdate());
    }

    public <V> V runInTransaction(final Callable<V> callable) throws Exception {
        userTransaction.begin();
        try {
            return callable.call();
        } catch (final Exception exception) {
            throw exception;
        } finally {
            userTransaction.commit();
        }
    }

}
