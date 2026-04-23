package ru.raidmine.raidpunishui.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.raidmine.raidpunishui.client.RaidPunishUiClient;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "handleTextClick", at = @At("HEAD"), cancellable = true)
    private void raidPunishUi$handleTextClick(@Nullable Style style, CallbackInfoReturnable<Boolean> cir) {
        if (style == null || style.getClickEvent() == null) {
            return;
        }

        if (RaidPunishUiClient.tryOpenFromClick(style.getClickEvent().getValue())) {
            cir.setReturnValue(true);
        }
    }
}
