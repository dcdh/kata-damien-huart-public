package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.WarmLimitMustBeSuperiorToColdLimitException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WarmLimitMustBeSuperiorToColdLimitExceptionMapper implements ExceptionMapper<WarmLimitMustBeSuperiorToColdLimitException> {

    @Override
    public Response toResponse(final WarmLimitMustBeSuperiorToColdLimitException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("Warm limit (%d°C) must be superior to cold limit (%d°C).",
                        exception.warmLimit().limit().temperature(),
                        exception.coldLimit().limit().temperature()))
                .build();
    }

}
