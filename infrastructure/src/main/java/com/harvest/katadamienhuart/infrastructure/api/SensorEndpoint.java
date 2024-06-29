package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureCommand;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesCommand;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
    @Produces(MediaType.APPLICATION_JSON)
    public SensorDTO askForTemperature() {
        return new SensorDTO(askForTemperatureUseCase.execute(new AskForTemperatureCommand()));
    }

    @GET
    @Path("/retrieveLast15Temperatures")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorHistoryDTO> retrieveLast15Temperatures() {
        return retrieveLast15TakenTemperaturesUseCase.execute(new RetrieveLast15TakenTemperaturesCommand())
                .stream()
                .map(SensorHistoryDTO::new)
                .toList();
    }

}
