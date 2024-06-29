package com.harvest.katadamienhuart.domain.usecase;

public interface UseCase<R extends Request, O, E extends Exception> {

    O execute(R request) throws E;

}
