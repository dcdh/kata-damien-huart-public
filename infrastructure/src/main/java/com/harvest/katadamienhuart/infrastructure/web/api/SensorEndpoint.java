package com.harvest.katadamienhuart.infrastructure.web.api;

import com.harvest.katadamienhuart.domain.usecase.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.Objects;

@Path("/sensor")
public class SensorEndpoint {

    private final AskForTemperatureUseCase askForTemperatureUseCase;
    private final RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase;

    public SensorEndpoint(final AskForTemperatureUseCase askForTemperatureUseCase,
                          final RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase) {
        this.askForTemperatureUseCase = Objects.requireNonNull(askForTemperatureUseCase);
        this.retrieveLast15TakenTemperaturesUseCase = Objects.requireNonNull(retrieveLast15TakenTemperaturesUseCase);
    }

    @GET
    @Path("/askForTemperature")
    @Produces("application/vnd.sensor-v1+json")
    @APIResponses(
            value = {
                    @APIResponse(
                            name = "success",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            type = SchemaType.OBJECT,
                                            implementation = SensorDTO.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Sensor following temperature taken",
                                                    //language=JSON
                                                    value = """
                                                            {
                                                              "sensorState": "WARM",
                                                              "takenTemperature": 22,
                                                              "takenAt": "2021-11-22T23:39:57.144382Z"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public SensorDTO askForTemperature() throws AskForTemperatureException {
        return new SensorDTO(askForTemperatureUseCase.execute(new AskForTemperatureRequest()));
    }

    @GET
    @Path("/retrieveLast15Temperatures")
    @Produces("application/vnd.sensor-history-v1+json")
    @APIResponses(
            value = {
                    @APIResponse(
                            name = "success",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            type = SchemaType.OBJECT,
                                            implementation = SensorDTO.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "History of last 15 temperatures taken for sensor",
                                                    //language=JSON
                                                    value = """
                                                            [
                                                              {
                                                                "sensorState": "WARM",
                                                                "takenTemperature": 22,
                                                                "takenAt": "2021-11-22T23:39:57.144382Z"
                                                              }
                                                            ]
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public List<SensorHistoryDTO> retrieveLast15Temperatures() throws RetrieveLast15TakenTemperaturesException {
        return retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesRequest())
                .stream()
                .map(SensorHistoryDTO::new)
                .toList();
    }

}
