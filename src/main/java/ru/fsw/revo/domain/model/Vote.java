package ru.fsw.revo.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import ru.fsw.revo.utils.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "votes")
@NamedQueries({
       @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant r LEFT JOIN FETCH r.menu m WHERE v.user.id=:userId  ORDER BY v.date DESC"),
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId"),
        @NamedQuery(name = Vote.GET, query = "SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant r WHERE v.id=:id AND v.user.id=:userId "),
        @NamedQuery(name = Vote.ALL_FOR_REST, query = "SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant r WHERE r.id=:rId"),
})
public class Vote extends AbstractBaseEntity {

    public static final String GET = "Vote.get";
    public static final String ALL_SORTED = "Vote.getAll";
    public static final String DELETE = "Vote.delete";
    public static final String GET_BETWEEN = "Vote.getBetween";
    public static final String ALL_FOR_REST = "Vote.getForRestaurant";

    @Column(name = "rating", nullable = false)
    @NotNull
    @Range(min = 0, max = 10)
    private int rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @Column(name = "datetime", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote(Long id, int rating, LocalDateTime date) {
        this.id = id;
        this.rating = rating;
        this.date = date;
    }

    public Vote() {
    }

    public Vote(Long id, int rating, Restaurant restaurant, LocalDateTime date) {
        this.id = id;
        this.restaurant = restaurant;
        this.rating = rating;
        this.date = date;
    }

    public Vote(Long id, int rating, Restaurant restaurant, LocalDateTime date, User user) {
        this.id = id;
        this.restaurant = restaurant;
        this.rating = rating;
        this.date = date;
        this.user = user;
    }
}
