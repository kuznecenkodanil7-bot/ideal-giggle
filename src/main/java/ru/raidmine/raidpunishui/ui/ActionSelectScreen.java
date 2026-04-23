package ru.raidmine.raidpunishui.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import ru.raidmine.raidpunishui.util.PunishmentAction;

public class ActionSelectScreen extends Screen {
    private final String target;

    public ActionSelectScreen(String target) {
        super(Text.literal("Выбор наказания"));
        this.target = target;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2 - 60;

        addDrawableChild(makeButton(centerX - 102, y, PunishmentAction.MUTE));
        addDrawableChild(makeButton(centerX + 2, y, PunishmentAction.WARN));
        addDrawableChild(makeButton(centerX - 102, y + 24, PunishmentAction.BAN));
        addDrawableChild(makeButton(centerX + 2, y + 24, PunishmentAction.IPBAN));
        addDrawableChild(ButtonWidget.builder(Text.literal("Отмена"), button -> close())
                .dimensions(centerX - 50, y + 60, 100, 20)
                .build());
    }

    private ButtonWidget makeButton(int x, int y, PunishmentAction action) {
        return ButtonWidget.builder(Text.literal(action.displayName()), button -> {
                    MinecraftClient.getInstance().setScreen(new DurationInputScreen(target, action));
                })
                .dimensions(x, y, 100, 20)
                .build();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 25, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Игрок: " + target), this.width / 2, 45, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
}
