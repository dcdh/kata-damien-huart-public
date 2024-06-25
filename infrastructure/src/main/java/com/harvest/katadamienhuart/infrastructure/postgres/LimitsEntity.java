package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "T_LIMITS")
@SequenceGenerator(
        name = "t_limits_seq",
        sequenceName = "t_limits_seq",
        initialValue = 1,
        allocationSize = 1 // Disable sequence cache
)
public final class LimitsEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_limits_seq")
    private Integer id;

    @NotNull
    private Integer coldLimit;

    @NotNull
    private Integer warmLimit;

    public LimitsEntity() {
    }

    public LimitsEntity(final Limits limits) {
        this.coldLimit = limits.coldLimit().limit().temperature();
        this.warmLimit = limits.warmLimit().limit().temperature();
    }

    public Limits toDomain() {
        return new Limits(new ColdLimit(
                new DegreeCelsius(coldLimit)),
                new WarmLimit(new DegreeCelsius(warmLimit)));
    }

    public static LimitsEntity findFirstOrderByIdDesc() {
        return findAll(Sort.descending("id")).firstResult();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitsEntity that = (LimitsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
