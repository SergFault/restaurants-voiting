package ru.fsw.revo.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.fsw.revo.utils.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "votes") //uniqueConstraints cannot be truncated to date tier
@NamedQueries({
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant r LEFT JOIN FETCH r.menu m WHERE v.user.id=:userId  ORDER BY v.date DESC",hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId"),
        @NamedQuery(name = Vote.GET, query = "SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant r WHERE v.id=:id AND v.user.id=:userId ", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = Vote.ALL_FOR_REST, query = "SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant r WHERE r.id=:rId", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = Vote.GET_FOR_REST_BETWEEN_DATES, query = "SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:rId AND v.date>= :startDate AND v.date< :endDate", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
})
@NamedEntityGraph(
        name = "vote_with_user_rest",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("restaurant")
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Vote extends AbstractBaseEntity {

    public static final String GET = "Vote.get";
    public static final String ALL_SORTED = "Vote.getAll";
    public static final String DELETE = "Vote.delete";
    public static final String GET_BETWEEN = "Vote.getBetween";
    public static final String ALL_FOR_REST = "Vote.getForRestaurant";
    public static final String GET_FOR_REST_BETWEEN_DATES = "Vote.getForRestaurantBetweenDates";

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
    @OnDelete(action = OnDeleteAction.CASCADE)
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
