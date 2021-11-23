package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.ColdLimitMustBePositiveException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ColdLimitMustBePositiveExceptionMapper implements ExceptionMapper<ColdLimitMustBePositiveException> {

    @Override
    public Response toResponse(final ColdLimitMustBePositiveException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("%d°C is invalid. ColdLimit must be a positive temperature.", exception.limit().temperature()))
                .build();
    }
}
