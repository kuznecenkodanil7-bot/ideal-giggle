package ru.raidmine.raidpunishui.util;

public enum PunishmentAction {
    MUTE("Мут", "mute"),
    WARN("Предупреждение", "warn"),
    BAN("Бан", "ban"),
    IPBAN("Бан по IP", "ipban");

    private final String displayName;
    private final String commandName;

    PunishmentAction(String displayName, String commandName) {
        this.displayName = displayName;
        this.commandName = commandName;
    }

    public String displayName() {
        return displayName;
    }

    public String commandName() {
        return commandName;
    }
}
