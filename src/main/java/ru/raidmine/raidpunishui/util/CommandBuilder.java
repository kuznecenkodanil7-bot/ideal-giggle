package ru.raidmine.raidpunishui.util;

public final class CommandBuilder {
    private CommandBuilder() {
    }

    public static String build(PunishmentAction action, String target, String duration, String reason) {
        String cleanDuration = duration == null ? "" : duration.trim();
        String cleanReason = reason == null ? "Без причины" : reason.trim();
        if (cleanReason.isEmpty()) {
            cleanReason = "Без причины";
        }
        return "/" + action.commandName() + " " + target + " " + cleanDuration + " " + cleanReason;
    }
}
