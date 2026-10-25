package com.crazyhacks.norender;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.IModule;
import org.rusherhack.client.api.feature.module.Module;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.plugin.Plugin;
import org.rusherhack.core.interfaces.IToggleable;
import org.rusherhack.core.setting.BooleanSetting;

/**
 * Adds a "Hotbar Item Names" option to RusherHack's NoRender. Made by lurda55.
 * If NoRender can't be found, the option comes as its own module (NoItemNames) instead.
 */
public class CrazyNoRenderPlugin extends Plugin {
	private static final BooleanSetting HOTBAR_ITEM_NAMES = new BooleanSetting("Hotbar Item Names",
			"Hide the item name that shows above the hotbar when you switch slot", true);
	private static IToggleable owner;

	@Override
	public void onLoad() {
		final IModule noRender = RusherHackAPI.getModuleManager().getFeature("NoRender").orElse(null);
		if(noRender instanceof Module module && noRender instanceof IToggleable toggleable) {
			// plugins load before the configs, so this setting is saved/loaded with NoRender
			module.registerSettings(HOTBAR_ITEM_NAMES);
			owner = toggleable;
			this.getLogger().info("Added Hotbar Item Names to NoRender");
		} else {
			final ToggleableModule fallback = new ToggleableModule("NoItemNames",
					"Hide the item name that shows above the hotbar when you switch slot", ModuleCategory.RENDER) {};
			HOTBAR_ITEM_NAMES.setVisibility(() -> false);
			fallback.registerSettings(HOTBAR_ITEM_NAMES);
			RusherHackAPI.getModuleManager().registerFeature(fallback);
			owner = fallback;
			this.getLogger().warn("NoRender not found, registered NoItemNames instead");
		}
	}

	@Override
	public void onUnload() {
		owner = null;
	}

	public static boolean hideHotbarItemNames() {
		return owner != null && owner.isToggled() && HOTBAR_ITEM_NAMES.getValue();
	}
}
