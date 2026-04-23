package ru.raidmine.raidpunishui.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import ru.raidmine.raidpunishui.ui.ActionSelectScreen;

public final class RaidPunishUiClient implements ClientModInitializer {
    public static final String CLICK_PREFIX = "/punishui ";
    private static String pendingTarget;

    @Override
    public void onInitializeClient() {
    }

    public static boolean tryOpenFromClick(String value) {
        if (value == null || !value.startsWith(CLICK_PREFIX)) {
            return false;
        }

        String target = value.substring(CLICK_PREFIX.length()).trim();
        if (target.isEmpty()) {
            return true;
        }

        openPunishScreen(target);
        return true;
    }

    public static void openPunishScreen(String target) {
        MinecraftClient client = MinecraftClient.getInstance();
        pendingTarget = target;
        if (client != null) {
            client.setScreen(new ActionSelectScreen(target));
        }
    }

    public static String getPendingTarget() {
        return pendingTarget;
    }
}
