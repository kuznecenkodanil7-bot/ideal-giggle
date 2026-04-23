package ru.raidmine.raidpunishui.util;

import java.util.Locale;
import java.util.regex.Pattern;

public final class DurationValidator {
    private static final Pattern PATTERN = Pattern.compile("(?i)^(?:\\d+[dh])(\\s+\\d+[dh])*$");

    private DurationValidator() {
    }

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        return !normalized.isEmpty() && PATTERN.matcher(normalized).matches();
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
    }
}
