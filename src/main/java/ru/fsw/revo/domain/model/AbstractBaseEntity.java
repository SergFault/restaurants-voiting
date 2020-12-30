package ru.fsw.revo.domain.model;

import lombok.Data;
import org.hibernate.Hibernate;
import ru.fsw.revo.utils.HasId;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class AbstractBaseEntity implements HasId {
    public static final int START_SEQ = 10000;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    protected Long id;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : Long.hashCode(id);
    }
}
