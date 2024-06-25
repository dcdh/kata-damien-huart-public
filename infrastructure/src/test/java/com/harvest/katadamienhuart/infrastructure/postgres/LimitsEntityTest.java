package com.harvest.katadamienhuart.infrastructure.postgres;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class LimitsEntityTest {

    @Test
    void should_verify_equality_on_id_only() {
        EqualsVerifier.forClass(LimitsEntity.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }
}