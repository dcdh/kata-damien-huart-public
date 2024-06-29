package com.harvest.katadamienhuart.domain.usecase;

import org.apache.commons.lang3.Validate;

import java.util.Objects;
import java.util.function.Function;

public class FailOnFalse implements Function<Boolean, Void> {
    private final String messageOnFail;

    public FailOnFalse(final String messageOnFail) {
        this.messageOnFail = Objects.requireNonNull(messageOnFail);
    }

    @Override
    public Void apply(final Boolean result) {
        Validate.validState(result, messageOnFail);
        return null;
    }
}
