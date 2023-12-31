package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.WarmLimitMustBePositiveException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WarmLimitMustBePositiveExceptionMapper implements ExceptionMapper<WarmLimitMustBePositiveException> {

    @Override
    public Response toResponse(final WarmLimitMustBePositiveException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("%d°C is invalid. WarmLimit must be a positive temperature.", exception.limit().temperature()))
                .build();
    }
}
