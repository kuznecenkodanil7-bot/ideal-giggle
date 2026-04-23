package ru.raidmine.raidpunishui.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import ru.raidmine.raidpunishui.util.DurationValidator;
import ru.raidmine.raidpunishui.util.PunishmentAction;

public class DurationInputScreen extends Screen {
    private final String target;
    private final PunishmentAction action;
    private TextFieldWidget durationField;
    private String errorText = "";

    public DurationInputScreen(String target, PunishmentAction action) {
        super(Text.literal("Срок наказания"));
        this.target = target;
        this.action = action;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2 - 35;

        durationField = new TextFieldWidget(this.textRenderer, centerX - 110, y, 220, 20, Text.literal("Срок"));
        durationField.setMaxLength(32);
        durationField.setPlaceholder(Text.literal("Примеры: 1d, 12h, 1d 12h"));
        durationField.setText("1h");
        addDrawableChild(durationField);
        setInitialFocus(durationField);

        addDrawableChild(ButtonWidget.builder(Text.literal("Далее"), button -> continueFlow())
                .dimensions(centerX - 110, y + 28, 106, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Назад"), button -> this.client.setScreen(new ActionSelectScreen(target)))
                .dimensions(centerX + 4, y + 28, 106, 20)
                .build());
    }

    private void continueFlow() {
        String raw = durationField.getText();
        if (!DurationValidator.isValid(raw)) {
            errorText = "Неверный формат. Используй d и h, например: 2d 6h";
            return;
        }

        String normalized = DurationValidator.normalize(raw);
        MinecraftClient.getInstance().setScreen(new ReasonSelectScreen(target, action, normalized, 0));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            continueFlow();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 25, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(action.displayName() + " → " + target), this.width / 2, 45, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("d — дни, h — часы"), this.width / 2, 63, 0xAAAAAA);
        if (!errorText.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(errorText), this.width / 2, this.height / 2 - 52, 0xFF5555);
        }
        durationField.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }
}
