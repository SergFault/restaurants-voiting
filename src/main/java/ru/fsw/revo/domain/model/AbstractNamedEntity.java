package ru.fsw.revo.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    protected AbstractNamedEntity() {

    }

    protected AbstractNamedEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}
