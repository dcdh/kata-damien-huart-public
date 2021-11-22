package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.usecase.GetTemperatureCommand;
import com.harvest.katadamienhuart.domain.usecase.GetTemperatureUseCase;
import com.harvest.katadamienhuart.infrastructure.postgres.PostgresSensorRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/sensor")
public class SensorEndpoint {

    private final GetTemperatureUseCase getTemperatureUseCase;
    private final PostgresSensorRepository postgresSensorRepository;

    public SensorEndpoint(final GetTemperatureUseCase getTemperatureUseCase,
                          final PostgresSensorRepository postgresSensorRepository) {
        this.getTemperatureUseCase = Objects.requireNonNull(getTemperatureUseCase);
        this.postgresSensorRepository = Objects.requireNonNull(postgresSensorRepository);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorDTO getTemperature() {
        return new SensorDTO(getTemperatureUseCase.execute(new GetTemperatureCommand()));
    }

    @GET
    @Path("/last15Temperatures")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorDTO> getLast15Temperatures() {
        return postgresSensorRepository.getLast15OrderedBySensedAtDesc()
                .stream()
                .map(SensorDTO::new).collect(Collectors.toList());
    }

}
