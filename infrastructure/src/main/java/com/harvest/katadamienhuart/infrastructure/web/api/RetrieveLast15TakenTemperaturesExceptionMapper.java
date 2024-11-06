package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Provider
public final class RetrieveLast15TakenTemperaturesExceptionMapper implements ExceptionMapper<RetrieveLast15TakenTemperaturesException> {
    private static final String VND_RETRIEVE_LAST_15_TAKEN_TEMPERATURES_ERROR_V1_TXT = "application/vnd.retrieve-last-15-taken-temperatures-error-v1+txt";
    private static final String UNKNOWN_MSG = "Something wrong happened";

    @Override
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "500", description = UNKNOWN_MSG,
                            content = {
                                    @Content(
                                            mediaType = VND_RETRIEVE_LAST_15_TAKEN_TEMPERATURES_ERROR_V1_TXT,
                                            schema = @Schema(
                                                    implementation = String.class),
                                            examples = {
                                                    @ExampleObject(
                                                            name = UNKNOWN_MSG,
                                                            value = UNKNOWN_MSG)
                                            }
                                    )
                            }
                    )
            }
    )
    public Response toResponse(final RetrieveLast15TakenTemperaturesException exception) {
        return switch (exception.getCause()) {
            default -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(VND_RETRIEVE_LAST_15_TAKEN_TEMPERATURES_ERROR_V1_TXT)
                    .entity(UNKNOWN_MSG)
                    .build();
        };
    }
}
