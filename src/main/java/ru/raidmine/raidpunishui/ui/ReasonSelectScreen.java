package ru.raidmine.raidpunishui.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import ru.raidmine.raidpunishui.util.CommandBuilder;
import ru.raidmine.raidpunishui.util.PunishmentAction;
import ru.raidmine.raidpunishui.util.RulePreset;

import java.util.List;

public class ReasonSelectScreen extends Screen {
    private static final int PAGE_SIZE = 8;

    private final String target;
    private final PunishmentAction action;
    private final String duration;
    private final int page;
    private TextFieldWidget reasonField;
    private String info = "";

    public ReasonSelectScreen(String target, PunishmentAction action, String duration, int page) {
        super(Text.literal("Причина наказания"));
        this.target = target;
        this.action = action;
        this.duration = duration;
        this.page = page;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int top = 52;

        reasonField = new TextFieldWidget(this.textRenderer, centerX - 150, top, 300, 20, Text.literal("Причина"));
        reasonField.setMaxLength(256);
        reasonField.setPlaceholder(Text.literal("Напиши свою причину или выбери шаблон ниже"));
        addDrawableChild(reasonField);
        setInitialFocus(reasonField);

        List<RulePreset> all = RulePreset.ALL;
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, all.size());
        int y = top + 30;
        for (int i = start; i < end; i++) {
            RulePreset preset = all.get(i);
            int localIndex = i - start;
            int column = localIndex % 2;
            int row = localIndex / 2;
            addDrawableChild(ButtonWidget.builder(Text.literal(preset.key()), button -> reasonField.setText(preset.text()))
                    .dimensions(centerX - 150 + column * 155, y + row * 24, 145, 20)
                    .build());
        }

        int maxPage = Math.max(0, (all.size() - 1) / PAGE_SIZE);
        ButtonWidget prevButton = addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> this.client.setScreen(new ReasonSelectScreen(target, action, duration, Math.max(0, page - 1))))
                .dimensions(centerX - 150, this.height - 54, 20, 20)
                .build());
        prevButton.active = page > 0;

        ButtonWidget nextButton = addDrawableChild(ButtonWidget.builder(Text.literal(">"), button -> this.client.setScreen(new ReasonSelectScreen(target, action, duration, Math.min(maxPage, page + 1))))
                .dimensions(centerX + 130, this.height - 54, 20, 20)
                .build());
        nextButton.active = page < maxPage;

        addDrawableChild(ButtonWidget.builder(Text.literal("Выдать"), button -> submit())
                .dimensions(centerX - 150, this.height - 28, 145, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Скопировать команду"), button -> copyCommand())
                .dimensions(centerX + 5, this.height - 28, 145, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Назад"), button -> this.client.setScreen(new DurationInputScreen(target, action)))
                .dimensions(centerX - 50, this.height - 54, 100, 20)
                .build());
    }

    private String buildCommand() {
        return CommandBuilder.build(action, target, duration, reasonField.getText());
    }

    private void submit() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.player.networkHandler == null) {
            info = "Игрок ещё не загружен";
            return;
        }

        String command = buildCommand();
        client.keyboard.setClipboard(command);
        client.player.networkHandler.sendCommand(command.startsWith("/") ? command.substring(1) : command);
        client.setScreen(null);
    }

    private void copyCommand() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        client.keyboard.setClipboard(buildCommand());
        info = "Команда скопирована в буфер обмена";
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            submit();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(action.displayName() + " → " + target + " → " + duration), this.width / 2, 36, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Шаблоны причин — для личного использования"), this.width / 2, 76, 0xAAAAAA);
        if (!info.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(info), this.width / 2, this.height - 68, 0x55FF55);
        }
        reasonField.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Страница " + (page + 1)), this.width / 2, this.height - 48, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
}
