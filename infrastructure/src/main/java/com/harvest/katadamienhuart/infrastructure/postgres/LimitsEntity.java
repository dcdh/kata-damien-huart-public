package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarnLimit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Integer warnLimit;

    public LimitsEntity() {}

    public LimitsEntity(final Limits limits) {
        this.coldLimit = limits.coldLimit().limit().temperature();
        this.warnLimit = limits.warnLimit().limit().temperature();
    }

    public Limits toDomain() {
        return new Limits(new ColdLimit(
                new DegreeCelsius(coldLimit)),
                new WarnLimit(new DegreeCelsius(warnLimit)));
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
