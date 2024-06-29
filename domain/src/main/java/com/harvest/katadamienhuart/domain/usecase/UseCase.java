package com.harvest.katadamienhuart.domain.usecase;

public interface UseCase<C extends Request, O> {

    O execute(C request);

}
