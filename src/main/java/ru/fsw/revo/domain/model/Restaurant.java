package ru.fsw.revo.domain.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity{

    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name = "dishes", joinColumns = {@JoinColumn(name = "rest_id", referencedColumnName = "id", nullable = false)})
    @MapKeyColumn(name = "dish_name")
    @Column(name = "price", nullable = false)
    private Map<String, Integer> menu = new HashMap<>();

    public Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Restaurant() {

    }
}
