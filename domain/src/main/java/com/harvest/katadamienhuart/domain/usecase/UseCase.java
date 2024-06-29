package com.harvest.katadamienhuart.domain.usecase;

public interface UseCase<R extends Request, O> {

    O execute(R request);

}
