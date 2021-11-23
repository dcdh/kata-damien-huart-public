package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.WarnLimitMustBePositiveException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WarnLimitMustBePositiveExceptionMapper implements ExceptionMapper<WarnLimitMustBePositiveException> {

    @Override
    public Response toResponse(final WarnLimitMustBePositiveException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("%d°C is invalid. WarnLimit must be a positive temperature.", exception.limit().temperature()))
                .build();
    }
}
