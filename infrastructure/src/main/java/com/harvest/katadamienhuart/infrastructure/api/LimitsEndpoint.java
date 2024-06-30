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
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
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
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_FORM_URLENCODED,
                    schema = @Schema(
                            type = SchemaType.OBJECT,
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
                    ),
                    // list of example not displayed when trying it
                    // https://github.com/swagger-api/swagger-ui/issues/10051
                    examples = {
                            @ExampleObject(
                                    name = "redefine ok",
                                    value = "newColdLimit=22&newWarmLimit=40"
                            ),
                            @ExampleObject(
                                    name = "redefine fail because new cold limit is negative",
                                    value = "newColdLimit=-22&newWarmLimit=40"
                            ),
                            @ExampleObject(
                                    name = "redefine fail because new warm limit is negative",
                                    value = "newColdLimit=22&newWarmLimit=-40"
                            ),
                            @ExampleObject(
                                    name = "redefine fail because new warm limit is below cold limit",
                                    value = "newColdLimit=40&newWarmLimit=22"
                            )
                    }
            )
    )
    @APIResponses(
            value = {
                    @APIResponse(
                            name = "success",
                            responseCode = "204"
                    )
            }
    )
    public void redefineLimits(@FormParam("newColdLimit") final Integer newColdLimit,
                               @FormParam("newWarmLimit") final Integer newWarmLimit)
            throws RedefineLimitsException {
        this.redefineLimitsUseCase.execute(new RedefineLimitsRequest(
                new ColdLimit(new DegreeCelsius(newColdLimit)),
                new WarmLimit(new DegreeCelsius(newWarmLimit))));
    }

}
