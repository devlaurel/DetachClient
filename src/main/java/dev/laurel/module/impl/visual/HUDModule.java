package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.Client;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.handler.ThemeManager;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "HUD", description = "Displays the client's HUD", moduleCategory = ModuleCategory.VISUAL)
public final class HUDModule extends Module {

    private final NumberSetting textBackgroundOpacity = new NumberSetting("TextBackgroundOpacity", 120, 0, 255, 5);
    private final BooleanSetting hideVisualModules = new BooleanSetting("HideVisualModules", false);
    private final ModeSetting colorTheme = new ModeSetting("ColorTheme", "DeepSea", "PornHub", "Tenacity", "Aqua", "Navy");

    public HUDModule() {
        this.setEnabled(true);
        this.addSettings(this.textBackgroundOpacity,this.hideVisualModules, this.colorTheme);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> {
        final ScaledResolution scaledResolution = event.getScaledResolution();

        final ArrayList<Module> modules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModules().values());
        modules.sort(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName() + module.getSuffix())));

        int count = 0;
        for (Module module : modules) {
            if (!module.isEnabled()) continue;
            final boolean hasModuleSuffix = !Objects.equals(module.getSuffix(), "");

            if (this.hideVisualModules.isEnabled() && module.getModuleCategory() == ModuleCategory.VISUAL) continue;
            Gui.drawRect(scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName() + (hasModuleSuffix ? " " + module.getSuffix() : "")) - 4, count * (mc.fontRendererObj.FONT_HEIGHT + 2), scaledResolution.getScaledWidth(), count * (mc.fontRendererObj.FONT_HEIGHT + 2) + mc.fontRendererObj.FONT_HEIGHT + 2, new Color(0, 0, 0, (int) textBackgroundOpacity.getValue()).getRGB());

            Color[] themeColors = ThemeManager.INSTANCE.getColorTheme(colorTheme.getCurrentMode());
            int color = ColorUtil.blend(themeColors[0], themeColors[1], (float) (Math.sin((System.currentTimeMillis() / 250D) % 1000 - count) + 1D) / 2F).getRGB();
            mc.fontRendererObj.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName() + (hasModuleSuffix ? " " + module.getSuffix() : "")) - 2, 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2), color);
            mc.fontRendererObj.drawStringWithShadow(module.getSuffix(), scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getSuffix()) - 2, 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2), Color.lightGray.getRGB());
            count++;
        }
    };
}
