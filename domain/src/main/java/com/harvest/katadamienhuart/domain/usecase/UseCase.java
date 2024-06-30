package com.harvest.katadamienhuart.domain.usecase;

public interface UseCase<R extends Request, O, E extends RuntimeException> {

    O execute(R request) throws E;

}
