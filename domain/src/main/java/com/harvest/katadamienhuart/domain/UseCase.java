package com.harvest.katadamienhuart.domain;

public interface UseCase<C extends UseCaseCommand, O> {

    O execute(C command);

}
