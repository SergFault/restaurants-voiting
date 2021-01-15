package ru.fsw.revo.utils;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.fsw.revo.utils.exception.VoteTimeModConstraint;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeUtil {
    private static Clock clock;

    public static final int HOUR_RESTRICTED = 11;
    public static final int MINUTES_RESTRICTED = 00;
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static void restrictionTimeCheck() {
        LocalDateTime dateOfVote = LocalDateTime.now(clock);
        dateOfVote.toLocalDate().atStartOfDay();
        if (!isBetweenHalfOpen(dateOfVote, dateOfVote.toLocalDate().atStartOfDay(), dateOfVote.toLocalDate().atTime(HOUR_RESTRICTED, MINUTES_RESTRICTED))) {
            throw new VoteTimeModConstraint("Vote can`t be changed after 11 A:M");
        }
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDateTime localDatetime) {
        return localDatetime != null ? localDatetime.toLocalDate().atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.plus(1, ChronoUnit.DAYS).toLocalDate().atStartOfDay() : MAX_DATE;
    }

    private DateTimeUtil(Clock clock) {
        this.clock = clock;
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDate.parse(str) : null;
    }

    public static @Nullable
    LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalTime.parse(str) : null;
    }

    public static @Nullable
    LocalDateTime parseLocalDateTime(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
