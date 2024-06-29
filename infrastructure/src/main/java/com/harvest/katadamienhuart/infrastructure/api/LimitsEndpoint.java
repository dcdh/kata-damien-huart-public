package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.WarmLimit;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsRequest;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
    @APIResponses(
            value = {
                    @APIResponse(
                            name = "success",
                            responseCode = "204"
                    )
            }
    )
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            required = true,
                            requiredProperties = {"newColdLimit", "newWarmLimit"},
                            properties = {
                                    @SchemaProperty(
                                            name = "newColdLimit",
                                            type = SchemaType.STRING,
                                            description = "New Cold limit to redefine",
                                            example = "22"
                                    ),
                                    @SchemaProperty(
                                            name = "newWarmLimit",
                                            type = SchemaType.STRING,
                                            description = "New Warm limit to redefine",
                                            example = "40"
                                    )
                            }
                    )
            )
    )
    public void redefineLimits(@FormParam("newColdLimit") final Integer newColdLimit,
                               @FormParam("newWarmLimit") final Integer newWarmLimit)
            throws RedefineLimitsException {
        try {
            this.redefineLimitsUseCase.execute(new RedefineLimitsRequest(
                    new ColdLimit(new DegreeCelsius(newColdLimit)),
                    new WarmLimit(new DegreeCelsius(newWarmLimit))));
        } catch (final Exception exception) {
            throw new RedefineLimitsException(exception);
        }
    }

}
