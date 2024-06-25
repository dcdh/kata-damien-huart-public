package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "T_LIMITS")
@SequenceGenerator(
        name = "t_limits_seq",
        sequenceName = "t_limits_seq",
        initialValue = 1,
        allocationSize = 1 // Disable sequence cache
)
public class LimitsEntity extends PanacheEntityBase {

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
