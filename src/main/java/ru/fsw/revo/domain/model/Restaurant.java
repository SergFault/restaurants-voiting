package ru.fsw.revo.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Restaurant.GET, query = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.id=:rId AND m.date=(SELECT max(m.date) FROM r.menu m where m.restaurant.id=:rId)"),
        @NamedQuery(name = Restaurant.GET_ALL, query = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE m.date=(SELECT max(m.date) FROM r.menu m) ORDER BY r.name ")

})
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_name_unique_idx")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurant extends AbstractNamedEntity {
    public static final String GET = "Restaurant.get";
    public static final String GET_ALL = "Get.all";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    @OrderBy(value = "name")
    private List<MenuItem> menu;

    public Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Restaurant(long id) {
        this.id = id;
    }

    public Restaurant() {
    }

    public Restaurant(String name, List<MenuItem> menu) {
        this.name = name;
        this.menu = menu;
    }

    public Restaurant(long id, String name, List<MenuItem> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }
}
