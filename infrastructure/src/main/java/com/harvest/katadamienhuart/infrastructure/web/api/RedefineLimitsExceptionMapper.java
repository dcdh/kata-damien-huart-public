package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.ColdLimitMustBePositiveException;
import com.harvest.katadamienhuart.domain.WarmLimitMustBePositiveException;
import com.harvest.katadamienhuart.domain.WarmLimitMustBeSuperiorToColdLimitException;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Provider
public final class RedefineLimitsExceptionMapper implements ExceptionMapper<RedefineLimitsException> {
    private static final String VND_REDEFINE_LIMIT_ERROR_V1_TXT = "application/vnd.redefine-limit-error-v1+txt";
    private static final String COLD_LIMIT_MUST_BE_POSITIVE_MSG = "%d°C is invalid. ColdLimit must be a positive temperature.";
    private static final String WARM_LIMIT_MUST_BE_POSITIVE_MSG = "%d°C is invalid. WarmLimit must be a positive temperature.";
    private static final String WARM_LIMIT_MUST_BE_SUPERIOR_TO_COLD_LIMIT_MSG = "Warm limit (%d°C) must be superior to cold limit (%d°C).";
    private static final String UNKNOWN_MSG = "Something wrong happened";

    @Override
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "400", description = "Input value error",
                            content = {
                                    @Content(
                                            mediaType = VND_REDEFINE_LIMIT_ERROR_V1_TXT,
                                            schema = @Schema(
                                                    implementation = String.class),
                                            examples = {
                                                    @ExampleObject(
                                                            name = "ColdLimit must be a positive temperature",
                                                            value = "-10°C is invalid. ColdLimit must be a positive temperature."),
                                                    @ExampleObject(
                                                            name = "WarmLimit must be a positive temperature",
                                                            value = "-10°C is invalid. WarmLimit must be a positive temperature."),
                                                    @ExampleObject(
                                                            name = "Warm limit must be superior to cold limit",
                                                            value = "Warm limit (10°C) must be superior to cold limit (20°C).")
                                            }
                                    )
                            }
                    ),
                    @APIResponse(responseCode = "500", description = UNKNOWN_MSG,
                            content = {
                                    @Content(
                                            mediaType = VND_REDEFINE_LIMIT_ERROR_V1_TXT,
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
    public Response toResponse(final RedefineLimitsException exception) {
        return switch (exception.getCause()) {
            case ColdLimitMustBePositiveException coldLimitMustBePositiveException ->
                    Response.status(Response.Status.BAD_REQUEST)
                            .type(VND_REDEFINE_LIMIT_ERROR_V1_TXT)
                            .entity(String.format(COLD_LIMIT_MUST_BE_POSITIVE_MSG, coldLimitMustBePositiveException.limit().temperature()))
                            .build();
            case WarmLimitMustBePositiveException warmLimitMustBePositiveException ->
                    Response.status(Response.Status.BAD_REQUEST)
                            .type(VND_REDEFINE_LIMIT_ERROR_V1_TXT)
                            .entity(String.format(WARM_LIMIT_MUST_BE_POSITIVE_MSG, warmLimitMustBePositiveException.limit().temperature()))
                            .build();
            case WarmLimitMustBeSuperiorToColdLimitException warmLimitMustBeSuperiorToColdLimitException ->
                    Response.status(Response.Status.BAD_REQUEST)
                            .type(VND_REDEFINE_LIMIT_ERROR_V1_TXT)
                            .entity(String.format(WARM_LIMIT_MUST_BE_SUPERIOR_TO_COLD_LIMIT_MSG,
                                    warmLimitMustBeSuperiorToColdLimitException.warmLimit().limit().temperature(),
                                    warmLimitMustBeSuperiorToColdLimitException.coldLimit().limit().temperature()))
                            .build();
            default -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(VND_REDEFINE_LIMIT_ERROR_V1_TXT)
                    .entity(UNKNOWN_MSG)
                    .build();
        };
    }
}
