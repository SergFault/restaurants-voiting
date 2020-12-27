package ru.fsw.revo.utils;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {
    private SecurityUtil() {
    }

    public static long authUserId() {
        return 10016;
    }
//    public static AuthorizedUser safeGet() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            return null;
//        }
//        Object principal = auth.getPrincipal();
//        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
//    }
//    public static AuthorizedUser get() {
//        return requireNonNull(safeGet(), "No authorized user found");
//    }
//
//    public static long authUserId() {
//        return get().getUserTo().id();
//    }
//
//    public static long authUserCaloriesPerDay() {
//        return get().getUserTo().getCaloriesPerDay();
//    }
}
