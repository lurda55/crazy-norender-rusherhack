package com.crazyhacks.norender.mixin;

import com.crazyhacks.norender.CrazyNoRenderPlugin;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
	// the item name that pops up above the hotbar when the selected slot changes
	@Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
	private void crazyNoRender$hideItemName(GuiGraphics graphics, CallbackInfo ci) {
		if(CrazyNoRenderPlugin.hideHotbarItemNames()) {
			ci.cancel();
		}
	}
}
