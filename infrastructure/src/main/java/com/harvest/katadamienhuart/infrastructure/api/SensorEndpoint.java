package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureCommand;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import com.harvest.katadamienhuart.infrastructure.postgres.PostgresSensorRepository;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/sensor")
public class SensorEndpoint {

    private final AskForTemperatureUseCase askForTemperatureUseCase;
    private final PostgresSensorRepository postgresSensorRepository;

    public SensorEndpoint(final AskForTemperatureUseCase askForTemperatureUseCase,
                          final PostgresSensorRepository postgresSensorRepository) {
        this.askForTemperatureUseCase = Objects.requireNonNull(askForTemperatureUseCase);
        this.postgresSensorRepository = Objects.requireNonNull(postgresSensorRepository);
    }

    @GET
    @Path("/askForTemperature")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorDTO getTemperature() {
        return new SensorDTO(askForTemperatureUseCase.execute(new AskForTemperatureCommand()));
    }

    @GET
    @Path("/retrieveLast15Temperatures")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorDTO> getLast15Temperatures() {
        return postgresSensorRepository.getLast15OrderedBySensedAtDesc()
                .stream()
                .map(SensorDTO::new).collect(Collectors.toList());
    }

}
