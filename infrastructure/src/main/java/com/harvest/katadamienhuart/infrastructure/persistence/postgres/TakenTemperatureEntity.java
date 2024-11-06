package com.harvest.katadamienhuart.infrastructure.persistence.postgres;

import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.TakenAt;
import com.harvest.katadamienhuart.domain.TakenTemperature;
import com.harvest.katadamienhuart.domain.Temperature;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "T_TAKEN_TEMPERATURE")
@SequenceGenerator(
        name = "t_taken_temperature_seq",
        sequenceName = "t_taken_temperature_seq",
        initialValue = 1,
        allocationSize = 1 // Disable sequence cache
)
public final class TakenTemperatureEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_taken_temperature_seq")
    private Integer id;

    @NotNull
    public ZonedDateTime takenAt;

    @NotNull
    public Integer takenTemperature;

    public TakenTemperatureEntity() {
    }

    public TakenTemperatureEntity(final TakenTemperature takenTemperature) {
        this.takenAt = takenTemperature.takenAt().at();
        this.takenTemperature = takenTemperature.temperature().degreeCelsius().temperature();
    }

    public TakenTemperature toTakenTemperature() {
        return new TakenTemperature(new Temperature(new DegreeCelsius(takenTemperature)),
                new TakenAt(takenAt));
    }

    public static Stream<TakenTemperatureEntity> findLast15OrderByTakenAtDesc() {
        return findAll(Sort.descending("takenAt")).page(0, 15).stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakenTemperatureEntity that = (TakenTemperatureEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
