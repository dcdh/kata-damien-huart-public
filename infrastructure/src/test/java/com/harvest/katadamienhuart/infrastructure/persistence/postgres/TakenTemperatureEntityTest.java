package com.harvest.katadamienhuart.infrastructure.persistence.postgres;

import com.harvest.katadamienhuart.infrastructure.persistence.postgres.TakenTemperatureEntity;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class TakenTemperatureEntityTest {

    @Test
    void should_verify_equality_on_id_only() {
        EqualsVerifier.forClass(TakenTemperatureEntity.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }
}