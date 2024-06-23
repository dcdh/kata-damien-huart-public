package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.usecase.TakeTemperatureCommand;
import com.harvest.katadamienhuart.domain.usecase.TakeTemperatureUseCase;
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

    private final TakeTemperatureUseCase takeTemperatureUseCase;
    private final PostgresSensorRepository postgresSensorRepository;

    public SensorEndpoint(final TakeTemperatureUseCase takeTemperatureUseCase,
                          final PostgresSensorRepository postgresSensorRepository) {
        this.takeTemperatureUseCase = Objects.requireNonNull(takeTemperatureUseCase);
        this.postgresSensorRepository = Objects.requireNonNull(postgresSensorRepository);
    }

    @GET
    @Path("/takeTemperature")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorDTO getTemperature() {
        return new SensorDTO(takeTemperatureUseCase.execute(new TakeTemperatureCommand()));
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
