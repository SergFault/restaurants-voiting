package ru.fsw.revo.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import ru.fsw.revo.util.DateTimeUtil;
import ru.fsw.revo.utils.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "votes")
@NamedQueries({
       @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant r LEFT JOIN FETCH r.menu m WHERE v.user.id=:userId  ORDER BY v.date DESC"),
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId"),
//        @NamedQuery(name = Vote.GET_BETWEEN, query = """
//                    SELECT v FROM Vote v
//                    WHERE v.user.id=:userId AND v.date >= :startDateTime AND v.date < :endDateTime ORDER BY v.date DESC
//                """)
})
public class Vote extends AbstractBaseEntity implements HasId {

    public static final String ALL_SORTED = "Vote.getAll";
    public static final String DELETE = "Vote.delete";
    public static final String GET_BETWEEN = "Vote.getBetween";

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

    @Override
    public Long id() {
        return 0L;
    }
}
