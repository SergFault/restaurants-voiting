package ru.fsw.revo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.fsw.revo.utils.exception.Vote11AMConstraint;

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

    public static void elevenCheck(LocalDateTime dateOfVote){
        LocalDateTime now  = LocalDateTime.now(clock);
        dateOfVote.toLocalDate().atStartOfDay();
        if (!isBetweenHalfOpen(LocalDateTime.now(clock), dateOfVote.toLocalDate().atStartOfDay(), dateOfVote.toLocalDate().atTime(HOUR_RESTRICTED,MINUTES_RESTRICTED) )){
            throw new Vote11AMConstraint("Vote can`t be changed after 11 A:M");
        }
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    private DateTimeUtil(Clock clock) {
        this.clock = clock;
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
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
