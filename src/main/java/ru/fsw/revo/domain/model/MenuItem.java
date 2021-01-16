package ru.fsw.revo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "dish")
@NamedQueries({
    @NamedQuery(name = "MenuItem.Clean", query = "DELETE FROM MenuItem m WHERE m.date =:today")
        })
public class MenuItem extends AbstractNamedEntity {

    public static final String CLEAN_FOR_TODAY = "MenuItem.Clean";

    public MenuItem() {
    }

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 0, max = 100000)
    private int price;

    @Column(name = "date_of", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    @JsonIgnore
    private Restaurant restaurant;

    public MenuItem(String name, int price){
        super(name);
        this.price = price;
    }

    public MenuItem(long id, String name, int price, Date date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
