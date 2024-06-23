package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "T_LIMITS")
public class LimitsEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer coldLimit;

    @NotNull
    private Integer warmLimit;

    public LimitsEntity() {}

    public LimitsEntity(final Limits limits) {
        this.coldLimit = limits.coldLimit().limit().temperature();
        this.warmLimit = limits.warmLimit().limit().temperature();
    }

    public Limits toDomain() {
        return new Limits(new ColdLimit(
                new DegreeCelsius(coldLimit)),
                new WarmLimit(new DegreeCelsius(warmLimit)));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LimitsEntity)) return false;
        final LimitsEntity that = (LimitsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
