package ru.raidmine.raidpunishui.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ClickableChatFormatter {
    private ClickableChatFormatter() {
    }

    public static Text makeNicknamesClickable(Text original) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.getNetworkHandler() == null) {
            return original;
        }

        String plain = original.getString();
        if (plain.isBlank()) {
            return original;
        }

        List<String> names = new ArrayList<>();
        for (PlayerListEntry entry : client.getNetworkHandler().getPlayerList()) {
            String name = entry.getProfile().getName();
            if (name != null && !name.isBlank()) {
                names.add(name);
            }
        }

        if (names.isEmpty()) {
            return original;
        }

        names.sort(Comparator.comparingInt(String::length).reversed());
        String union = names.stream().map(Pattern::quote).reduce((a, b) -> a + "|" + b).orElse("");
        Pattern pattern = Pattern.compile("(?<![A-Za-z0-9_])(" + union + ")(?![A-Za-z0-9_])");
        Matcher matcher = pattern.matcher(plain);

        int index = 0;
        MutableText result = Text.empty();
        boolean found = false;
        while (matcher.find()) {
            found = true;
            if (matcher.start() > index) {
                result.append(Text.literal(plain.substring(index, matcher.start())));
            }

            String nick = matcher.group(1);
            result.append(Text.literal(nick).setStyle(Style.EMPTY
                    .withUnderline(true)
                    .withClickEvent(new ClickEvent.SuggestCommand("/punishui " + nick))));
            index = matcher.end();
        }

        if (!found) {
            return original;
        }

        if (index < plain.length()) {
            result.append(Text.literal(plain.substring(index)));
        }

        return result;
    }
}
