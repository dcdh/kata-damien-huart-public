package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.WarmLimit;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsCommand;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import java.util.Objects;

@Path("/limits")
public class LimitsEndpoint {

    private final RedefineLimitsUseCase redefineLimitsUseCase;

    public LimitsEndpoint(final RedefineLimitsUseCase redefineLimitsUseCase) {
        this.redefineLimitsUseCase = Objects.requireNonNull(redefineLimitsUseCase);
    }

    @POST
    @Path("/redefineLimits")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void redefineLimits(@FormParam("newColdLimit") final Integer newColdLimit,
                               @FormParam("newWarmLimit") final Integer newWarmLimit) {
        this.redefineLimitsUseCase.execute(new RedefineLimitsCommand(
                new ColdLimit(new DegreeCelsius(newColdLimit)),
                new WarmLimit(new DegreeCelsius(newWarmLimit))));
    }

}
